package 并发编程03.手写原生Lock;

/**
 * @Author linhao
 * @Date created in 5:29 下午 2022/6/9
 */
public interface ILock {

    void lock();

    void unlock();
}
