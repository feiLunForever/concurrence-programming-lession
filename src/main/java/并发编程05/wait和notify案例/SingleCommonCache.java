package 并发编程05.wait和notify案例;


/**
 * @Author linhao
 * @Date created in 8:51 上午 2022/6/13
 */
public class SingleCommonCache {

    private static int size = 10;
    private static Object[] MSG_QUEUE = new Object[size];
    private static int putIndex = 0;
    private static int consumerIndex = 0;

    public static boolean putMsg(String msg) {
        if (putIndex == size) {
            System.err.println("消息队列体积已达到上限！");
            return false;
        }
        MSG_QUEUE[putIndex++] = msg;
        return true;
    }
}
