package 并发编程07.producer_consumer模式;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @Author linhao
 * @Date created in 6:33 下午 2022/5/17
 */
public class Consumer {

    private ArrayBlockingQueue<String> queue;

    public Consumer(ArrayBlockingQueue<String> queue) {
        this.queue = queue;
    }

    public void start() {
        Thread t = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        String content = queue.take();
                        System.out.println("消费数据：" + content);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
    }
}
