package 并发编程17.手动实现本地缓存.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @Author idea
 * @Date created in 8:50 上午 2022/8/29
 */
public class CleanUpThread implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(CleanUpThread.class);
    private int time;
    private CacheUtils cacheUtils;

    public CleanUpThread(int time, CacheUtils cacheUtils) {
        this.cacheUtils = cacheUtils;
        this.time = time;
    }

    @Override
    public void run() {
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(time);
                //将过期的数据进行清理
                expireTimeCleanUp();
                //如果存储数据达到最大值，这里回进行清理工作
                maxmumSizeCleanUp();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 内存空间回收清理工作
     */
    private void maxmumSizeCleanUp() {
        LOGGER.debug("[maxmumSizeCleanUp] start clean up");
        int maxmumSize = cacheUtils.getCacheGlobal().getMaxmumSize();
        //如果有配置最大容量，这里会进行回收处理
        int currentSize = cacheUtils.getCacheGlobal().cacheConcurrentHashMap.size();
        if (maxmumSize == 0 || currentSize < maxmumSize) {
            return;
        }
        CacheGlobal cacheGlobal = cacheUtils.getCacheGlobal();
        ConcurrentHashMap<String, MyCache> tempMap = cacheGlobal.cacheConcurrentHashMap;
        List<Map.Entry<String, MyCache>> entrys = new ArrayList<>(tempMap.entrySet());
        Collections.sort(entrys, new Comparator<Map.Entry<String, MyCache>>() {
            @Override
            public int compare(Map.Entry<String, MyCache> o1, Map.Entry<String, MyCache> o2) {
                return (int) (o2.getValue().getHitCount().get() - o1.getValue().getHitCount().get());
            }
        });
        //只保留一半的缓存数据
        int delSize = (currentSize - maxmumSize) + (currentSize / 2);
        int i = 0;
        ConcurrentHashMap<String, MyCache> newCacheMap = new ConcurrentHashMap<>();
        //保留一半的热点数据
        for (Map.Entry<String, MyCache> entry : entrys) {
            if (i >= delSize) {
                break;
            }
            //访问数考后的冷数据需要进行移除
            cacheUtils.remove(entry.getKey());
            i++;
        }
    }

    /**
     * 过期数据清理工作
     */
    private void expireTimeCleanUp() {
        LOGGER.debug("[expireTimeCleanUp] start clean up");
        CacheGlobal cacheGlobal = cacheUtils.getCacheGlobal();
        ConcurrentHashMap<String, MyCache> tempMap = cacheGlobal.cacheConcurrentHashMap;
        for (String cacheKey : tempMap.keySet()) {
            MyCache myCache = tempMap.get(cacheKey);
            if (myCache == null) {
                continue;
            }
            long timeoutTime = System.currentTimeMillis() - myCache.getWriteTime();
            if (myCache.getExpireTime() * 1000 > timeoutTime) {
                continue;
            }
            //清除过期缓存key
            cacheUtils.remove(cacheKey);
        }
    }

}
