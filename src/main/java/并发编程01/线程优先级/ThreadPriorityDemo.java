package 并发编程01.线程优先级;

/**
 * 优先级和操作系统及虚拟机版本相关。
 * 优先级只是代表告知了 「线程调度器」该线程的重要度有多大。如果有大量线程都被堵塞，都在等候运
 * 行，调试程序会首先运行具有最高优先级的那个线程。然而，这并不表示优先级较低的线程不会运行（换言之，不会因为存在优先级而导致死锁）。
 * 若线程的优先级较低，只不过表示它被准许运行的机会小一些而已。
 *
 * @Author linhao
 * @Date created in 6:16 下午 2022/5/18
 */
public class ThreadPriorityDemo {

    static class InnerTask implements Runnable {

        private int i;

        public InnerTask(int i) {
            this.i = i;
        }

        public void run() {
            for(int j=0;j<10;j++){
                System.out.println("ThreadName is " + Thread.currentThread().getName()+" "+j);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new InnerTask(10),"task-1");
        t1.setPriority(1);
        Thread t2 = new Thread(new InnerTask(2),"task-2");
        //优先级只能作为一个参考数值，而且具体的线程优先级还和操作系统有关
        t2.setPriority(2);
        Thread t3 = new Thread(new InnerTask(3),"task-3");
        t3.setPriority(3);

        t1.start();
        t2.start();
        t3.start();
        Thread.sleep(2000);
    }
}
