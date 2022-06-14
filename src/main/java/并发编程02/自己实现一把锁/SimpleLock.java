package 并发编程02.自己实现一把锁;

import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

/**
 * 简单的一把锁
 *
 * @Author linhao
 * @Date created in 10:45 上午 2022/5/20
 */
public class SimpleLock implements ILock{

    private AtomicReference<Thread> owner = new AtomicReference<>();
    private LinkedBlockingQueue<Thread> threadQueue = new LinkedBlockingQueue<>();

    @Override
    public void lock() {
        Thread thread = Thread.currentThread();
        while (!owner.compareAndSet(null,thread)){
            threadQueue.offer(thread);
            LockSupport.park();
            threadQueue.remove(thread);
        }
    }

    @Override
    public void unlock() {
        Thread thread = Thread.currentThread();
        while (owner.compareAndSet(thread,null)){
            Iterator<Thread> iterator = threadQueue.iterator();
            while (iterator.hasNext()){
                Thread next = iterator.next();
                if(next!=null){
                    LockSupport.unpark(next);
                }
            }
        }
    }
}
