package 并发编程16.分布式锁核心实现.lock.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import 并发编程16.分布式锁核心实现.lock.IDistributionLock;
import 并发编程16.分布式锁核心实现.lock.LockStatusThreadLock;
import 并发编程16.分布式锁核心实现.redis.IRedisService;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * @Author idea
 * @Date created in 10:04 下午 2022/8/24
 */
public class DistributionLock implements IDistributionLock {

    private IRedisService redisService;
    private ExecutorService watchTaskPool = Executors.newFixedThreadPool(1);

    public DistributionLock(IRedisService redisService) {
        this.redisService = redisService;
        LOGGER.info(" =================== [DistributionLock] init WatchKeyTask  =================== ");
        watchTaskPool.execute(new WatchKeyTask());
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(DistributionLock.class);
    //用于记录每个锁的名字，方便后期进行续命的时候操作
    private static Set<String> lockKeySet = new HashSet<>();
    //记录每个线程是否都有加锁成功过
    private static LockStatusThreadLock lockStatusThreadLock = new LockStatusThreadLock();
    private static final int DEFAULT_LOCK_TIME = 5;
    private static final int RESET_EXPIRE_TIME = 1;

    @Override
    public Boolean tryLock(String key) {
        //默认锁的超时时间是5秒，
        if (redisService.setNx(key, 1, DEFAULT_LOCK_TIME)) {
            //加锁成功，则记录当前线程获取锁成功
            lockStatusThreadLock.set(true);
            //记录到set中，方便续命使用
            lockKeySet.add(key);
            LOGGER.info(" =================== [DistributionLock] tryLock success =================== ");
            return true;
        } else {
            LOGGER.info(" =================== [DistributionLock] tryLock fail =================== ");
            return false;
        }
    }

    @Override
    public Boolean tryLockNotReSet(String key) {
        //默认锁的超时时间是5秒，
        if (redisService.setNx(key, 1, DEFAULT_LOCK_TIME)) {
            //加锁成功，则记录当前线程获取锁成功
            lockStatusThreadLock.set(true);
            LOGGER.info(" =================== [DistributionLock] tryLock success =================== ");
            return true;
        } else {
            LOGGER.info(" =================== [DistributionLock] tryLock fail =================== ");
            return false;
        }
    }


    /**
     * 给锁进行续命的定时任务
     */
    class WatchKeyTask implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(3000);
                    LOGGER.debug("[DistributionLock] exec WatchKeyTask");
                    for (String lockKey : lockKeySet) {
                        long expireTime = redisService.ttl(lockKey);
                        //这个时候表明key已经不存在了
                        if (expireTime == -2) {
                            //二次删除确认
                            lockKeySet.remove(lockKey);
                            continue;
                        }
                        //如果锁的存活时间只剩下了25%，而且此时还是处于加锁状态，则进行续命
                        if (RESET_EXPIRE_TIME > expireTime) {
                            //如果此时锁已经被删除了，这里也不要紧
                            //如果此时锁已经被另一个线程拥有了，这里新线程的锁的时间会被重置一次，不过不影响
                            redisService.expire(lockKey, DEFAULT_LOCK_TIME, TimeUnit.SECONDS);
                            LOGGER.info("[DistributionLock] expire lock {}", lockKey);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Boolean tryLock(String key, int tryTimes) {
        int i = 0;
        while (i < tryTimes) {
            if (tryLock(key)) {
                return true;
            }
            i++;
        }
        return false;
    }

    @Override
    public Boolean releaseLock(String key) {
        try {
            if (lockStatusThreadLock.get()) {
                LOGGER.info("[DistributionLock] releaseLock, key is {}", key);
                redisService.del(key);
                return true;
            }
        } catch (Exception e) {
            LOGGER.error("[DistributionLock] releaseLock has error,e is ", e);
        } finally {
            //如果锁不需要续命，这里执行一次也没有什么影响
            lockKeySet.remove(key);
            lockStatusThreadLock.remove();
        }
        return false;
    }
}
