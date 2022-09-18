package 并发编程18.forkjoin使用案例;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * 使用forkjoin进行任务计算
 * 查找一批数据中最大的数字
 *
 * @Author idea
 * @Date created in 8:02 上午 2022/9/1
 */
public class ForkJoinFindBigNumberDemo {

    public static class MyTask extends RecursiveTask<Integer> {
        int[] innerSrc;

        public MyTask(int[] innerSrc) {
            this.innerSrc = innerSrc;
        }


        @Override
        protected Integer compute() {
            if (innerSrc.length < 2) {
                if (innerSrc.length == 1) {
                    return innerSrc[0];
                } else {
                    return innerSrc[0] > innerSrc[1] ? innerSrc[0] : innerSrc[1];
                }
            } else {
                int mid = innerSrc.length / 2;
                MyTask leftTask = new MyTask(Arrays.copyOf(innerSrc, mid));
                MyTask rightTask = new MyTask(Arrays.copyOfRange(innerSrc, mid, innerSrc.length));
                invokeAll(leftTask, rightTask);
                return leftTask.join() > rightTask.join() ? leftTask.join() : rightTask.join();
            }
        }
    }


    public static void main(String[] args) {
        int[] src = {1, 2, 3, 4, 5, 6, 7, 18, 9, 10, 11, 12};
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        MyTask totalTask = new MyTask(src);
        forkJoinPool.invoke(totalTask);
        System.out.println("the max number is " + totalTask.join());
    }
}
