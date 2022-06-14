package 并发编程07.producer_consumer模式;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @Author linhao
 * @Date created in 5:38 下午 2022/5/17
 */
public class Producer {

    private ArrayBlockingQueue<String> queue;

    public Producer(ArrayBlockingQueue<String> queue) {
        this.queue = queue;
    }

    public boolean put(String content){
        try {
            queue.put(content);
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void take(){
        queue.poll();
    }
}
