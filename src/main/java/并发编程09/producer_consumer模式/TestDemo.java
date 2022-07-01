package 并发编程09.producer_consumer模式;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @Author linhao
 * @Date created in 6:33 下午 2022/5/17
 */
public class TestDemo {
    public static ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(20);

    public static void main(String[] args) throws InterruptedException {
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);
        consumer.start();
        while (true){
            System.out.println("投递元素");
            producer.put(UUID.randomUUID().toString());
            Thread.sleep(2000);
        }
    }

}
