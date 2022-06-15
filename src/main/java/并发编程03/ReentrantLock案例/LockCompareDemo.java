package 并发编程03.ReentrantLock案例;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Jdk中Lock使用技巧
 *
 * @Author linhao
 * @Date created in 11:31 上午 2022/6/3
 */
public class LockCompareDemo {

    private Integer i = new Integer(1);
    private ReentrantLock reentrantLock = new ReentrantLock();

    public void lockMethod() {
        //lock操作要放置在try代码块之外，
        //因为finally中的unlock要求锁的monitor对象需要持有，否则会抛出 IllegalMonitorStateException 异常
        reentrantLock.lock();
        try {
            i++;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }

    public void tryLockMethod() {
        if (reentrantLock.tryLock()) {
            try {
                i++;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
            }
        } else {
            //todo
        }
    }

    public void tryLockMethod_2() {
        try {
            if (reentrantLock.tryLock(1, TimeUnit.SECONDS)) {
                try {
                    i++;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    reentrantLock.unlock();
                }
            } else {
                //todo
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void lockInterruptiblyMethod() {
        try {
            reentrantLock.lockInterruptibly();
            try {
                i++;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
