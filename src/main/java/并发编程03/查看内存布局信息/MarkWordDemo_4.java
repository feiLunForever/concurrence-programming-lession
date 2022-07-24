package 并发编程03.查看内存布局信息;

import org.openjdk.jol.info.ClassLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author linhao
 * @Date created in 7:14 下午 2022/7/22
 */
public class MarkWordDemo_4 {

    public static final Logger LOGGER = LoggerFactory.getLogger(MarkWordDemo_4.class);

    public static void main(String[] args) throws InterruptedException {
        System.out.println("未进入同步块，MarkWord 为：" + ClassLayout.parseInstance(new Object()).toPrintable());
        // 睡眠 5s
        Thread.sleep(5000);
        Object b = new Object();
        System.out.println("未进入同步块，MarkWord 为：" + ClassLayout.parseInstance(b).toPrintable());
        synchronized (b) {
            System.out.println("进入同步块，MarkWord 为：" + ClassLayout.parseInstance(b).toPrintable());
        }
        Thread t2 = new Thread(() -> {
            synchronized (b) {
                System.out.println("新线程获取锁，MarkWord为：");
                System.out.println(ClassLayout.parseInstance(b).toPrintable());
            }
        });

        t2.start();
        t2.join();
        System.out.println("主线程再次查看锁对象，MarkWord为：");
        System.out.println(ClassLayout.parseInstance(b).toPrintable());

        synchronized (b) {
            System.out.println(("主线程再次进入同步块，MarkWord 为："));
            System.out.println(ClassLayout.parseInstance(b).toPrintable());
        }

        synchronized (b) {
            System.out.println(("主线程再次进入同步块，并且调用hashcode方法，MarkWord 为："));
            b.hashCode();
            System.out.println(ClassLayout.parseInstance(b).toPrintable());
        }
    }
}
