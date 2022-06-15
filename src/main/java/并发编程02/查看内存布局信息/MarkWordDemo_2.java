package 并发编程02.查看内存布局信息;

import org.openjdk.jol.info.ClassLayout;

/**
 * @Author linhao
 * @Date created in 8:16 上午 2022/6/7
 */
public class MarkWordDemo_2 {

    static class T {
        int m;
        long j;
        boolean flag; //
        //这个String只是一个引用，只会占用4byte，真正的存储空间是放在堆中（常量池）
        String t = "xxxxxxxxx";

    }

    public static void main(String[] args) {
        T t = new T();
        //查看该对象的内存布局
        System.out.println(ClassLayout.parseInstance(t).toPrintable());
    }
}
