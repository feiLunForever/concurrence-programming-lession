package 并发编程17.本地缓存测试;


import org.junit.jupiter.api.Test;
import 并发编程17.手动实现本地缓存.core.CacheUtils;
import 并发编程17.手动实现本地缓存.listener.AddListener;
import 并发编程17.手动实现本地缓存.listener.ReadListener;
import 并发编程17.手动实现本地缓存.listener.RefreshListener;
import 并发编程17.手动实现本地缓存.listener.RemovalListener;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author idea
 * @Date created in 7:29 下午 2022/8/29
 */
public class TestCache {

    private CacheUtils cache = null;

    @Test
    public void testExpire() throws InterruptedException {
        cache = new CacheUtils(-1, false);
        cache.put("key1", "value1", 2);
        System.out.println(cache.get("key1"));
        Thread.sleep(2500);
        System.out.println(cache.get("key1"));
    }

    @Test
    public void testExpireThread() throws InterruptedException {
        cache = new CacheUtils(5, true);
        cache.put("key1", "value1", 2);
        System.out.println(cache.containKey("key1"));
        Thread.sleep(7000);
        System.out.println(cache.containKey("key1"));
    }

    @Test
    public void testAddListener() throws InterruptedException {
        cache = new CacheUtils(5, true).registryAddListener(new AddListener() {
            @Override
            public void onAdd(String key, Object value) {
                System.out.println("key :" + key + " value :" + value);
            }
        });
        cache.put("key1", "value1", 3);
        Thread.sleep(100);
    }

    @Test
    public void testRemoveListener() throws InterruptedException {
        cache = new CacheUtils(2, true).registryRemoveListener(new RemovalListener() {
            @Override
            public void onRemoval(String key) {
                System.out.println("key :" + key);
            }
        });
        cache.put("key1", "value1", 1);
        Thread.sleep(6000);
    }


    @Test
    public void testReadListener() throws InterruptedException {
        cache = new CacheUtils(5, true).registryReadListener(new ReadListener() {
            @Override
            public void onRead(String key, Object value) {
                System.out.println("key :" + key + " value :" + value);
            }
        });
        cache.put("key1", "value1", 5);
        cache.get("key1");
        Thread.sleep(2000);
        System.out.println("after");
        cache.get("key1");
    }

    @Test
    public void testMaxmumSizeCleanUp() throws InterruptedException {
        cache = new CacheUtils(5, true).registryRemoveListener(new RemovalListener() {
            @Override
            public void onRemoval(String key) {
                System.out.println("remove key:" + key);
            }
        }).maxmumSize(4);
        cache.put("key1","value1",200);
        cache.put("key2","value2",200);
        cache.put("key3","value2",200);
        cache.put("key4","value2",200);
        cache.put("key5","value2",200);
        Thread.sleep(10000);
        System.out.println(cache.size());
    }

    @Test
    public void testRefreshListener() throws InterruptedException {
        cache = new CacheUtils(5, true);
        cache.registryRefreshListener(new RefreshListener() {
            @Override
            public void doRefresh() {
                Map<String, Object> tempMap = new HashMap<>();
                tempMap.put("k1", 1);
                tempMap.put("k2", 2);
                tempMap.put("k3", 3);
                for (String key : tempMap.keySet()) {
                    cache.put(key, tempMap.get(key), 100);
                }
            }
        },5);
        System.out.println(cache.size());
        Thread.sleep(6000);
        System.out.println(cache.size());
    }

    //测试使用本地缓存组件
    @Test
    public void testUseCache() throws InterruptedException {
        cache = new CacheUtils(5, true);
        cache.registryRefreshListener(new RefreshListener() {

            @Override
            public void doRefresh() {
                cache.clear();
                cache.put("key1", 1, 100);
                cache.put("key2", 2, 100);
                cache.put("key3", 3, 100);
            }

        },3).registryReadListener(new ReadListener() {
            @Override
            public void onRead(String key, Object value) {
                System.out.println("key :" + key + " value :" + value);
            }
        }).registryAddListener(new AddListener() {
            @Override
            public void onAdd(String key, Object value) {
                System.out.println("key :" + key + " value :" + value);
            }
        }).registryRemoveListener(new RemovalListener() {
            @Override
            public void onRemoval(String key) {
                System.out.println("remove key:" + key);

            }
        });
        Thread.sleep(7000);
        cache.put("key4",4,100);
        cache.put("key5",5,100);
        System.out.println(cache.size());
        System.out.println(cache.get("key4"));
    }

}
