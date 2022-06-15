package 并发编程03.查看内存布局信息;

import org.openjdk.jol.info.ClassLayout;

/**
 * @Author linhao
 * @Date created in 8:05 上午 2022/6/7
 */
public class MarkWordDemo_1 {

    public static void main(String[] args) {
        Object o = new Object();
        //查看该对象的内存布局
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
    }
}
