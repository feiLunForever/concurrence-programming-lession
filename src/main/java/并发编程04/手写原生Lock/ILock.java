package 并发编程04.手写原生Lock;

/**
 * @Author idea
 * @Date created in 5:29 下午 2022/6/9
 */
public interface ILock {

    void lock();

    void unlock();
}
