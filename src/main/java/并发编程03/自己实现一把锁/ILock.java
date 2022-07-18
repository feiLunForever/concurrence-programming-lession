package 并发编程03.自己实现一把锁;

/**
 * @Author idea
 * @Date created in 10:43 上午 2022/5/20
 */
public interface ILock {

    /**
     * 加锁
     */
    void lock();

    /**
     * 解锁
     *
     * @return true 解锁成功，false 解锁失败
     */
    void unlock();

}
