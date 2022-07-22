package 并发编程13;

/**
 * @Author linhao
 * @Date created in 7:23 下午 2022/7/22
 */
public interface ICache<K,V> {

    V get(K key);

    void put(K key,V value);
}
