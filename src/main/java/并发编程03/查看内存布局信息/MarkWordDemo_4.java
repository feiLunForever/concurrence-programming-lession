package 并发编程03.查看内存布局信息;

import org.openjdk.jol.info.ClassLayout;

/**
 * @Author linhao
 * @Date created in 7:14 下午 2022/7/22
 */
public class MarkWordDemo_4 {

    public static void main(String[] args) throws InterruptedException {
        // 睡眠 5s
        Thread.sleep(5000);
        Object o = new Object();
        System.out.println("未进入同步块，MarkWord 为：");
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
        synchronized (o){
            System.out.println(("进入同步块，MarkWord 为："));
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }

        Thread t2 = new Thread(() -> {
            synchronized (o) {
                System.out.println("新线程获取锁，MarkWord为：");
                System.out.println(ClassLayout.parseInstance(o).toPrintable());
            }
        });

        t2.start();
        t2.join();
        System.out.println("主线程再次查看锁对象，MarkWord为：");
        System.out.println(ClassLayout.parseInstance(o).toPrintable());

        synchronized (o){
            System.out.println(("主线程再次进入同步块，MarkWord 为："));
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }
    }
}
