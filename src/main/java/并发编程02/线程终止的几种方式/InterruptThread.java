package 并发编程02.线程终止的几种方式;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author idea
 * @Date created in 8:41 上午 2022/6/15
 */
public class InterruptThread {


    static class TestThread implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.print(i + " ");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class TestThreadWithSync implements Runnable {

        @Override
        public void run() {
            synchronized (this) {
                for (int i = 20; i < 30; i++) {
                    System.out.print(i + " ");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    static class TestThreadWithLock implements Runnable {

        ReentrantLock reentrantLock = new ReentrantLock();

        @Override
        public void run() {
            reentrantLock.lock();
            try {
                for (int i = 30; i < 40; i++) {
                    System.out.print(i + " ");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("test");
                }
            } finally {
                reentrantLock.unlock();
            }
        }
    }

    static class TestInterruptedStop implements Runnable {

        @Override
        public void run() {
            synchronized (this) {
                //如果当前线程被中断，这里需要主动退出
                while (!Thread.currentThread().isInterrupted()) {
                }
                System.out.println("end");
            }
        }
    }

    static class ForEverThread implements Runnable {

        @Override
        public void run() {
            System.out.println("开始执行");
            while (true) {

            }
        }

    }

    public static void main(String[] args) throws InterruptedException {
        Thread testThread = new Thread(new TestThread());
        testThread.start();
        Thread testThreadWithSync = new Thread(new TestThreadWithSync());
        testThreadWithSync.start();
        Thread testThreadWithLock = new Thread(new TestThreadWithLock());
        testThreadWithLock.start();
        Thread forEverThread = new Thread(new ForEverThread());
        forEverThread.start();
        Thread testInterruptedStop = new Thread(new TestInterruptedStop());
        testInterruptedStop.start();

        Thread.sleep(2000);
        testInterruptedStop.interrupt();
        Thread.sleep(1000);
        System.out.println("testInterruptedStop is interrupted:" + testInterruptedStop.isInterrupted());
        forEverThread.interrupt();
        testThread.interrupt();
        testThreadWithSync.interrupt();
        testThreadWithLock.interrupt();

    }

}
