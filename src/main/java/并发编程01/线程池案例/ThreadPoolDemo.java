package 并发编程01.线程池案例;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author linhao
 * @Date created in 11:18 上午 2022/5/19
 */
public class ThreadPoolDemo {

    public static ExecutorService executors = Executors.newFixedThreadPool(1);

    public static void main(String[] args) throws InterruptedException {
        executors.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("test");
            }
        });
        Thread.sleep(200);
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("test-2");
            }
        });
        t1.start();
        t1.stop();
        t1.suspend();
        t1.resume();
    }
}
