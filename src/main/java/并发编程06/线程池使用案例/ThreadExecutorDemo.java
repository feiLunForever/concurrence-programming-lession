package 并发编程06.线程池使用案例;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 使用线程池案例
 *
 * @Author idea
 * @Date created in 10:06 上午 2022/6/19
 */
public class ThreadExecutorDemo {

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                1,
                1,
                1,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000));
        threadPoolExecutor.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("this is submitTask");
            }
        });
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("this is executeTask");
            }
        });
    }
}
