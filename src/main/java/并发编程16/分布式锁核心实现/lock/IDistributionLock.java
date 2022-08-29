package 并发编程16.分布式锁核心实现.lock;

/**
 * @Author idea
 * @Date created in 10:03 下午 2022/8/24
 */
public interface IDistributionLock {


    /**
     * 加锁
     *
     * @param key 锁的名字
     * @return
     */
    Boolean tryLock(String key);


    /**
     * 加锁(不会续命)
     *
     * @param key 锁的名字
     * @return
     */
    Boolean tryLockNotReSet(String key);


    /**
     * 加锁
     * @param key 锁的key
     * @param tryTimes 重试次数
     */
    Boolean tryLock(String key,int tryTimes);

    /**
     * 释放锁
     *
     * @param key
     * @return
     */
    Boolean releaseLock(String key);
}
