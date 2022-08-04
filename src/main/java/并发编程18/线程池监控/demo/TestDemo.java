package 并发编程18.线程池监控.demo;

import io.netty.util.concurrent.DefaultThreadFactory;
import 并发编程18.线程池监控.executor.IExecutor;
import 并发编程18.线程池监控.executor.IExecutorJob;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author linhao
 * @Date created in 8:44 下午 2022/8/3
 */
public class TestDemo {

    public static void main(String[] args) {
        IExecutor executor = new IExecutor(1,1,1000, TimeUnit.MILLISECONDS
        ,"test-pool","testExecutor",new ArrayBlockingQueue<>(10),new DefaultThreadFactory("test-thread")
                , new ThreadPoolExecutor.AbortPolicy(),true,true,true);
        IExecutorJob job = new IExecutorJob("测试任务", new Runnable() {
            @Override
            public void run() {
                System.out.println("test");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        executor.execute(job);

    }
}
