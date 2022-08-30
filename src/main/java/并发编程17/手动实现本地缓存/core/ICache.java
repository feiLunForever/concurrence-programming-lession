package 并发编程17.手动实现本地缓存.core;

/**
 * @Author idea
 * @Date created in 11:58 上午 2022/8/30
 */
public interface ICache {

    /**
     * 清楚全部缓存
     */
    void clear();

    /**
     * 放入缓存
     *
     * @param key
     * @param value
     * @param needDelAfterRead
     * @param expire
     * @return
     */
    Object put(String key, Object value, boolean needDelAfterRead, long expire);

    /**
     * 放入缓存
     *
     * @param key
     * @param value
     * @param expire
     * @return
     */
    Object put(String key, Object value, long expire);

    /**
     * 从缓存中取出数据
     *
     * @param key
     * @return
     */
    Object get(String key);

    /**
     * 缓存中是否存在key
     *
     * @param key
     * @return
     */
    boolean containKey(String key);

    /**
     * 移除key
     *
     * @param key
     * @return
     */
    boolean remove(String key);
}
