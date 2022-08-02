package 并发编程14.限流组件.redis;


/**
 * @Author linhao
 * @Date created in 5:45 下午 2022/7/29
 */
public interface IRedisSemaphore {

    /**
     * 设置一段时间内，最多只有多少个请求
     *
     * @param name
     * @param limit
     * @param expireTime
     * @return
     */
    boolean tryAcquire(String name,int limit,int expireTime);


    /**
     * 释放锁
     *
     * @return
     */
    boolean release();

}
