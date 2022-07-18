package 并发编程02.线程池关闭;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author idea
 * @Date created in 10:21 下午 2022/6/16
 */
public class ExecutorShutDownDemo2 {

    public static void testShutDown() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("[testShutDown] begin");
                    for(int i=0;i<10;i++){
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
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
                    for(int i=0;i<10;i++){
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
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

    //通过实验发现，直接调用shutDown不会立即结束线程池内线程的任务，而是会等待线程完成任务。
    public static void main(String[] args) throws InterruptedException {
        testShutDown();
//        testShutDownNow();
    }
}
