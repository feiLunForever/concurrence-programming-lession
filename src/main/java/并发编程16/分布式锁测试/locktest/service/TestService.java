package 并发编程16.分布式锁测试.locktest.service;

import org.springframework.stereotype.Service;
import 并发编程16.分布式锁核心实现.lock.IDistributionLock;
import 并发编程16.分布式锁核心实现.redis.IRedisService;

import javax.annotation.Resource;

/**
 * @Author idea
 * @Date created in 10:29 下午 2022/8/24
 */
@Service
public class TestService {

    @Resource
    private IDistributionLock distributionLock;
    @Resource
    private IRedisService redisService;

    public static String LOCK_KEY = "lock_key";

    /**
     * 模拟扣减库存
     */
    public void doDecrStoreWithLock() {
        boolean lockStatue = false;
        try {
            lockStatue = distributionLock.tryLock(LOCK_KEY);
            if (lockStatue) {
                String storeKey = "store";
                int store = redisService.getInt(storeKey);
                if (store == 0) {
                    System.out.println("库存已经扣减完毕");
                } else {
                    //如果库存尚有剩余，这里直接扣减
                    redisService.decr(storeKey);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (lockStatue) {
                distributionLock.releaseLock(LOCK_KEY);
            }
        }
    }

    /**
     * 模拟扣减库存
     */
    public void doDecrStoreNotWithLock() {
        String storeKey = "store";
        int store = redisService.getInt(storeKey);
        if (store == 0) {
            System.out.println("库存已经扣减完毕");
        } else {
            //如果库存尚有剩余，这里直接扣减
            redisService.decr(storeKey);
        }
    }


}
