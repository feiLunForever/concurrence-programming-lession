package 并发编程17.手动实现本地缓存.listener;

/**
 * @Author idea
 * @Date created in 10:36 下午 2022/8/29
 */
@FunctionalInterface
public interface ReadListener {

    void onRead(String key, Object value);
}
