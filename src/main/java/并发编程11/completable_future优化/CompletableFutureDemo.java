package 并发编程11.completable_future优化;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 使用CompletableFuture的底层原理
 *
 * @Author linhao
 * @Date created in 2:35 下午 2022/7/6
 */
public class CompletableFutureDemo {


    @Test
    public void whenCompleteAndExceptionally() throws ExecutionException, InterruptedException {
        CompletableFuture<Double> future = CompletableFuture.supplyAsync(() -> {
            if (Math.random() < 0.5) {
                throw new RuntimeException("出错了");
            }
            System.out.println("结果正常！");
            return 0.11;
            //不管是否有异常，都会执行到whenComplete函数中
        }).whenComplete(new BiConsumer<Double, Throwable>() {
            @Override
            public void accept(Double aDouble, Throwable throwable) {
                if (aDouble == null) {
                    System.out.println("when complete aDouble is null");
                } else {
                    System.out.println("when complete aDouble is " + aDouble);
                }
                if (throwable == null) {
                    System.out.println("when complete throwable is null");
                } else {
                    System.out.println("when complete throwable is " + throwable.getMessage());
                }
            }
            //如果有异常的话，可以在exceptionally中做处理，这样的话在调用get函数时就不会抛出异常
        }).exceptionally(new Function<Throwable, Double>() {
            @Override
            public Double apply(Throwable throwable) {
                System.out.println("exceptionally中异常：" + throwable.getMessage());
                return null;
            }
        });
        System.out.println("最终返回的结果 = " + future.get());
    }


    @Test
    public void supplyAsyncAndRunAsync() {
        //supplyAsync带有返回结果
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                return "test";
            }
        });
        //runAsync不带有返回结果
        CompletableFuture completableFuture1 = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                System.out.println("test");
            }
        });
    }

    @Test
    public void completeAndExceptionallyWithForkJoinPool() {
        ForkJoinPool pool = new ForkJoinPool();
        // 创建异步执行任务:
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread() + " start,time->" + System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            System.out.println(Thread.currentThread() + " exit,time->" + System.currentTimeMillis());
            return 1.2;
        }, pool);
        System.out.println("main thread start,time->" + System.currentTimeMillis());
    }

    @Test
    public void supplyAsyncAndThenApply(){
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                System.out.println(Thread.currentThread().getName());
                return "test";
            }
        });

        CompletableFuture<String> completableFuture1 = completableFuture.thenApply(new Function<String, String>() {
            @Override
            public String apply(String s) {
                System.out.println(s);
                System.out.println(Thread.currentThread().getName());
                return "idea";
            }
        });
        CompletableFuture.allOf();
        CompletableFuture.anyOf();
    }


    @Test
    public void testGetAndJoin() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                System.out.println(1/0);
                return "success";
            }
        });
//        String result = completableFuture.get();
        String result2 = completableFuture.join();
    }

}
