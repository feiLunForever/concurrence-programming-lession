package 并发编程03.wait调用;

/**
 * @Author linhao
 * @Date created in 8:07 上午 2022/5/24
 */
public class WaitDemo {


    public void tryWait() throws InterruptedException {
        synchronized (this){
            System.out.println("进入堵塞状态");
            Thread.sleep(2000);
            wait();
            System.out.println("堵塞结束！");
        }
    }

    public void notifyWait() throws InterruptedException {
        synchronized (this) {
            System.out.println("准备进入通知");
            notifyAll();
            System.out.println("通知结束");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        WaitDemo waitDemo = new WaitDemo();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    waitDemo.tryWait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    waitDemo.notifyWait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        Thread.sleep(500);
        t2.start();
    }
}
