package 并发编程04.Condition案例;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author linhao
 * @Date created in 8:37 上午 2022/6/8
 */
public class ConditionDemo_1 {

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public static void main(String[] args) {
        ConditionDemo_1 conditionDemo = new ConditionDemo_1();
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                conditionDemo.waitTime();
            }
        });
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                conditionDemo.wakeUp();
            }
        });
        LockSupport.park();

    }

    public void waitTime(){
        lock.lock();
        try {
            System.out.println("before wait");
            condition.await();
            System.out.println("after wait");
        }catch (Exception e){
        }
        lock.unlock();
    }

    public void wakeUp(){
        lock.lock();
        try {
            System.out.println("before wake up");
            condition.signal();
            condition.signalAll();
            System.out.println("after wake up");
            Thread.sleep(1000);
        }catch (Exception e){
        }
        lock.unlock();
    }

}
