package 并发编程20.动态修改线程池属性;

import lombok.SneakyThrows;

import java.util.concurrent.*;

/**
 * @Author linhao
 * @Date created in 8:17 上午 2022/9/13
 */
public class ChangeTaskPoolField {

    public static int corePoolSize = 1;
    public static int maximumPoolSize = 1000;
    public static int queueSize = 100;

    public static void main(String[] args) {

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 3, TimeUnit.SECONDS, new ArrayBlockingQueue<>(queueSize));
        Thread runTaskThread = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                while (true) {
                    int corePoolSize = threadPoolExecutor.getCorePoolSize();
                    int maxiMumPoolSize = threadPoolExecutor.getMaximumPoolSize();
                    int remainingCapacity = threadPoolExecutor.getQueue().remainingCapacity();
                    System.out.println("核心线程数：" + corePoolSize + ",最大线程数限制：" + maxiMumPoolSize + ",剩余队列长度：" + remainingCapacity);
                    TimeUnit.SECONDS.sleep(2);
                }
            }
        });
        runTaskThread.start();

        Thread watchFieldThread = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                while (true) {
                    threadPoolExecutor.setCorePoolSize(corePoolSize++);
                    threadPoolExecutor.setMaximumPoolSize(maximumPoolSize++);
                    TimeUnit.SECONDS.sleep(1);
                }
            }
        });
        watchFieldThread.start();
    }
}
