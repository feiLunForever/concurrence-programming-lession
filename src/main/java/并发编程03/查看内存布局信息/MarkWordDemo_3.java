package 并发编程03.查看内存布局信息;

import org.openjdk.jol.info.ClassLayout;

/**
 * @Author idea
 * @Date created in 5:27 下午 2022/7/22
 */
public class MarkWordDemo_3 {

    public static void main(String[] args) throws InterruptedException {

        Object o = new Object();
        System.out.println("还没有进入到同步块");
        System.out.println("markword:" + ClassLayout.parseInstance(o).toPrintable());
        //默认JVM启动会有一个预热阶段，所以默认不会开启偏向锁
        Thread.sleep(5000);
        Object b = new Object();
        System.out.println("还没有进入到同步块");
        System.out.println("markword:" + ClassLayout.parseInstance(b).toPrintable());
        synchronized (o){
            System.out.println("进入到了同步块");
            System.out.println("markword:" + ClassLayout.parseInstance(o).toPrintable());
        }
    }
}
