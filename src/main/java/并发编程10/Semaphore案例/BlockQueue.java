package 并发编程10.Semaphore案例;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @Author linhao
 * @Date created in 2:38 下午 2022/7/15
 */
public class BlockQueue {

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    private Semaphore semaphore;
    private List<Object> list;

    private int currentIndex = 0;

    private int maxLen;

    public BlockQueue(int size) {
        this.list = new ArrayList(size * 2);
        this.maxLen = size;
        this.semaphore = new Semaphore(size);
    }

    public void add(Integer item) {
        if (currentIndex == maxLen) {
            throw new IndexOutOfBoundsException("队列存储空间有限，不能再放入新的元素");
        }
        list.add(item);
        semaphore.release(1);
    }

    public Object take(long time, TimeUnit timeUnit) {
        if (list.size()==0 || list.get(0) == null) {
            try {
                //如果没有放入新的元素，这里会阻塞
                semaphore.tryAcquire(time,timeUnit);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
        Object currentValue = list.get(0);
        list.remove(0);
        return currentValue;
    }


    public static void main(String[] args) {
        BlockQueue blockQueue = new BlockQueue(10);
        Thread writeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<100;i++){
                    blockQueue.add(i);
                }
            }
        });
        writeThread.start();
        Thread readThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<101;i++) {
                    System.out.println(blockQueue.take(3,TimeUnit.SECONDS));
                }
            }
        });
        readThread.start();

    }
}
