package 并发编程10.CyclicBarrier案例;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @Author idea
 * @Date created in 8:43 下午 2022/6/29
 */
public class MultiRequestHandler {

    private CyclicBarrier cyclicBarrier = new CyclicBarrier(10);

    public static void req_1() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void reportSuccess() {
        System.out.println("执行结束");
    }

    static class ConcurrentReqThread implements Runnable {

        private CyclicBarrier cyclicBarrier;

        public ConcurrentReqThread(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            System.out.println("do request!");
            req_1();
            try {
                cyclicBarrier.await();
                reportSuccess();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void concurrentReq() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(10);
        for (int i = 0; i < 10; i++) {
            Thread cyclicBarrierThread = new Thread(new ConcurrentReqThread(cyclicBarrier));
            cyclicBarrierThread.start();
        }
    }

    public static void main(String[] args) {
        concurrentReq();
    }
}
