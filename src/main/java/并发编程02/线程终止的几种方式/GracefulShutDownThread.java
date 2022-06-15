package 并发编程02.线程终止的几种方式;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author linhao
 * @Date created in 10:05 上午 2022/6/15
 */
public class GracefulShutDownThread {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("this is a task");
                    while (true){

                    }
                }catch (Exception e){
                    e.printStackTrace();
                } finally {
                    System.out.println("end");
                }
            }
        });
        executorService.shutdown();
        System.out.println("结束所有线程");


    }
}
