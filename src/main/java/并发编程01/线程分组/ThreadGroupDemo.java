package 并发编程01.线程分组;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @Author idea
 * @Date created in 6:05 下午 2022/5/18
 */
public class ThreadGroupDemo {

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static List<Thread> DbConnThread() {
        ThreadGroup dbConnThreadGroup = new ThreadGroup("数据库连接线程组");
        List<Thread> dbConnThreadList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Thread t = new Thread(dbConnThreadGroup, new Runnable() {
                @Override
                public void run() {
                    System.out.println("线程名: " + Thread.currentThread().getName()
                            + ", 所在线程组: " + Thread.currentThread().getThreadGroup().getName());
                    try {
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "db-conn-thread-" + i);
            dbConnThreadList.add(t);
        }
        return dbConnThreadList;
    }

    public static List<Thread> httpReqThread() {
        ThreadGroup httpReqThreadGroup = new ThreadGroup("第三方http请求线程组");
        List<Thread> httpReqThreadList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Thread t = new Thread(httpReqThreadGroup, new Runnable() {
                @Override
                public void run() {
                    System.out.println("线程名: " + Thread.currentThread().getName()
                            + ", 所在线程组: " + Thread.currentThread().getThreadGroup().getName());
                    try {
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "http-req-thread-" + i);
            httpReqThreadList.add(t);
        }
        return httpReqThreadList;
    }

    public static void startThread(List<Thread> threadList) {
        for (Thread thread : threadList) {
            thread.start();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        List<Thread> dbConnThreadList = DbConnThread();
        List<Thread> httpReqThreadList = httpReqThread();
        startThread(dbConnThreadList);
        startThread(httpReqThreadList);
        Thread.sleep(3000000);
    }
}
