package 并发编程02.线程池关闭;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author idea
 * @Date created in 10:21 下午 2022/6/16
 */
public class ExecutorShutDownDemo {

    public static void testShutDown() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("[testShutDown] begin");
                    while (true) {
                    }
                }finally {
                    System.out.println("[testShutDown] end");
                }
            }
        });
        Thread.sleep(2000);
        executorService.shutdown();
        System.out.println("[testShutDown] shutdown");
    }

    public static void testShutDownNow() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("[testShutDown] begin");
                    while (true) {
                    }
                }finally {
                    System.out.println("[testShutDown] end");
                }
            }
        });
        Thread.sleep(2000);
        executorService.shutdownNow();
        System.out.println("[testShutDown] shutdownNow");
    }

    public static void main(String[] args) throws InterruptedException {
        testShutDown();
        testShutDownNow();
    }
}
