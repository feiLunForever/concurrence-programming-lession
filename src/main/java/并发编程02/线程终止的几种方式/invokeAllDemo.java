package 并发编程02.线程终止的几种方式;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class invokeAllDemo {

    public static void main(String[] args) throws Exception {
        ExecutorService service = Executors.newFixedThreadPool(2);
        ArrayList<Callable<Object>> list = new ArrayList<Callable<Object>>();
        for (int i = 0; i < 2; i++) {
            list.add(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    System.out.println(Thread.currentThread().getName());
                    return null;
                }
            });
        }
        //线程池中的每个任务执行完之后都会回调到这里
        service.invokeAll(list);
        System.out.println("可以汇总计算了！");
        service.shutdown();
    }
}

 

