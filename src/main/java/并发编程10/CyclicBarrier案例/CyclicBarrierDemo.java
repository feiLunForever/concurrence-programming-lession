package 并发编程10.CyclicBarrier案例;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @Author idea
 * @Date created in 8:27 下午 2022/6/29
 */
public class CyclicBarrierDemo {

    static class WaitStart implements Runnable{

        private CyclicBarrier cyclicBarrier;

        public WaitStart(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                System.out.println("准备干活！");
                cyclicBarrier.await();
                System.out.println("开始动手！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
        List<Thread> threadList = new ArrayList<>();
        for(int i=0;i<5;i++){
            Thread thread = new Thread(new WaitStart(cyclicBarrier));
            thread.start();
        }
        System.out.println("start");
    }
}
