package 并发编程02.线程终止的几种方式;

import java.util.concurrent.TimeUnit;

/**
 * @Author idea
 * @Date created in 8:29 上午 2022/6/17
 */
public class ThreadSuspendDemo {

    /**
     * 暂停线程
     */
    static class SuspendThread implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                System.out.print(i+" ");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        Thread suspendThread = new Thread(new SuspendThread());
        suspendThread.start();
        TimeUnit.SECONDS.sleep(5);
        System.out.print("线程暂停");
        suspendThread.suspend();
        TimeUnit.SECONDS.sleep(2);
        suspendThread.resume();
        System.out.print(" 线程恢复");
    }
}
