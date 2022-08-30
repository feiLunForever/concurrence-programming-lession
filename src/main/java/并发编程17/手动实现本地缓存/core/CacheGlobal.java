package 并发编程17.手动实现本地缓存.core;

import 并发编程17.手动实现本地缓存.listener.AddListener;
import 并发编程17.手动实现本地缓存.listener.ReadListener;
import 并发编程17.手动实现本地缓存.listener.RemovalListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author idea
 * @Date created in 8:49 上午 2022/8/29
 */
public class CacheGlobal {

    public ConcurrentHashMap<String, MyCache> cacheConcurrentHashMap = new ConcurrentHashMap<>();
    /**
     * 检查缓存过期的频率（单位：秒）
     */
    private int checkTime;
    /**
     * 是否需要支持清理
     */
    private boolean needClean;

    /**
     * 缓存中可以存放的最大个数，达到则需要进行回收
     */
    private int maxmumSize;

    /**
     * 对key进行监听的监听器
     */
    private List<AddListener> addListeners = new ArrayList<>();
    private List<RemovalListener> removalListeners = new ArrayList<>();
    private List<ReadListener> readListeners = new ArrayList<>();

    public CacheGlobal(int checkTime, boolean needClean) {
        this.checkTime = checkTime;
        this.needClean = needClean;
    }

    public CacheGlobal maxmumSize(int maxmumSize) {
        this.maxmumSize = maxmumSize;
        return this;
    }

    public CacheGlobal addAddListener(AddListener addListener) {
        this.addListeners.add(addListener);
        return this;
    }

    public CacheGlobal addReadListener(ReadListener readListener) {
        this.readListeners.add(readListener);
        return this;
    }

    public CacheGlobal addRemovalListener(RemovalListener removalListener) {
        this.removalListeners.add(removalListener);
        return this;
    }


    public static CacheGlobal DEFAULT_CACHE() {
        return new CacheGlobal(5, true);
    }

    public int getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(int checkTime) {
        this.checkTime = checkTime;
    }

    public boolean isNeedClean() {
        return needClean;
    }

    public void setNeedClean(boolean needClean) {
        this.needClean = needClean;
    }

    public List<AddListener> getAddListeners() {
        return addListeners;
    }

    public void setAddListeners(List<AddListener> addListeners) {
        this.addListeners = addListeners;
    }

    public List<RemovalListener> getRemovalListeners() {
        return removalListeners;
    }

    public void setRemovalListeners(List<RemovalListener> removalListeners) {
        this.removalListeners = removalListeners;
    }

    public int getMaxmumSize() {
        return maxmumSize;
    }

    public void setMaxmumSize(int maxmumSize) {
        this.maxmumSize = maxmumSize;
    }

    public List<ReadListener> getReadListeners() {
        return readListeners;
    }

    public void setReadListeners(List<ReadListener> readListeners) {
        this.readListeners = readListeners;
    }
}
