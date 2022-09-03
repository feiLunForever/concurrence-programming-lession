package 并发编程18.forkjoin使用案例;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 使用forkJoin执行求和计算任务
 *
 * @Author idea
 * @Date created in 5:30 下午 2022/9/1
 */
public class ForkJoinSumDemo {

    public static AtomicInteger countTime = new AtomicInteger(0);

    static class ArraySumTask extends RecursiveTask<Long> {

        private static final int grain = 2000;
        private int begin, end;
        private long[] src;

        public ArraySumTask(int begin, int end, long[] src) {
            this.begin = begin;
            this.end = end;
            this.src = src;
        }

        @Override
        protected Long compute() {
            Long count = 0L;
            if (end - begin <= grain) {
                System.out.println("个数：" + (end - begin));
                for (int i = begin; i < end; i++) {
                    count += src[i];
                }
            } else {
                int mid = (begin + end) / 2;
                RecursiveTask left = new ArraySumTask(begin, mid, src);
                RecursiveTask right = new ArraySumTask(mid, end, src);
                invokeAll(left, right);
                long leftJoin = (long) left.join();
                long rightJoin = (long) right.join();
                count = leftJoin + rightJoin;
            }
            System.out.println("计算第" + countTime.getAndIncrement() + "次");
            return count;
        }
    }

    public static Random random = new Random();

    public static void main(String[] args) {
        int total = 10000;
        long[] data = new long[total];
        long testCount = 0;
        //随机填充数据
        for (int i = 0; i < total; i++) {
            data[i] = random.nextInt(5);
            testCount += data[i];
        }
        ArraySumTask arraySumTask = new ArraySumTask(0, data.length, data);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        long sum = forkJoinPool.invoke(arraySumTask);
        System.out.println(sum);
        System.out.println(testCount);
    }
}
