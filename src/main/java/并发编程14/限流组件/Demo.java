package 并发编程14.限流组件;

import java.util.concurrent.Semaphore;

/**
 * @Author linhao
 * @Date created in 3:35 下午 2022/7/24
 */
public class Demo {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(11);
    }
}
