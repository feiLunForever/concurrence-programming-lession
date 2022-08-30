package 并发编程17.手动实现本地缓存.core;

import io.netty.util.internal.StringUtil;
import 并发编程17.手动实现本地缓存.listener.AddListener;
import 并发编程17.手动实现本地缓存.listener.ReadListener;
import 并发编程17.手动实现本地缓存.listener.RefreshListener;
import 并发编程17.手动实现本地缓存.listener.RemovalListener;


/**
 * @Author idea
 * @Date created in 8:54 上午 2022/8/29
 */
public class CacheUtils implements ICache{

    //这里面主要是缓存的配置数据和存储的map集合
    private CacheGlobal cacheGlobal;

    public CacheUtils() {
        this.initConfig(CacheGlobal.DEFAULT_CACHE());
    }

    public CacheUtils(int checkTime, boolean needClean) {
        cacheGlobal = new CacheGlobal(checkTime, needClean);
        this.initConfig(cacheGlobal);
    }


    /**
     * 初始化缓存配置
     *
     * @param cacheGlobal
     */
    public void initConfig(CacheGlobal cacheGlobal) {
        if (cacheGlobal.isNeedClean()) {
            Thread cleanThread = new Thread(new CleanUpThread(cacheGlobal.getCheckTime(), this));
            cleanThread.start();
        }
    }

    @Override
    public void clear(){
        cacheGlobal.cacheConcurrentHashMap.clear();
    }

    /**
     * 将某个key放入缓存中
     *
     * @param key
     * @param value
     * @param needDelAfterRead
     * @param expire
     * @return
     */
    @Override
    public Object put(String key, Object value, boolean needDelAfterRead, long expire) {
        if (StringUtil.isNullOrEmpty(key)) {
            return value;
        }
        //如果存在 则更新缓存key的值
        MyCache item = cacheGlobal.cacheConcurrentHashMap.get(key);
        if (item != null) {
            item = new MyCache();
            item.getHitCount().incrementAndGet();
            item.setWriteTime(System.currentTimeMillis());
            item.setDelAfterRead(needDelAfterRead);
            item.setLastTime(System.currentTimeMillis());
            item.setExpireTime(expire);
            item.setValue(value);
        } else {
            item = new MyCache();
            item.setKey(key);
            item.setDelAfterRead(needDelAfterRead);
            item.getHitCount().incrementAndGet();
            item.setLastTime(System.currentTimeMillis());
            item.setWriteTime(System.currentTimeMillis());
            item.setExpireTime(expire);
            item.setValue(value);
        }
        cacheGlobal.cacheConcurrentHashMap.put(key, item);
        MyCache finalItem = item;
        cacheGlobal.getAddListeners().forEach(x -> {
            x.onAdd(key, finalItem);
        });
        return item;
    }

    /**
     * 放入缓存
     *
     * @param key
     * @param value
     * @param expire 过期时间，默认单位秒
     */
    @Override
    public Object put(String key, Object value, long expire) {
        return this.put(key,value,false,expire);
    }

    /**
     * 取出缓存
     *
     * @param key
     */
    @Override
    public Object get(String key) {
        if (StringUtil.isNullOrEmpty(key)) {
            cacheGlobal.getReadListeners().forEach(x -> {
                x.onRead(key, null);
            });
            return null;
        }
        MyCache item = cacheGlobal.cacheConcurrentHashMap.get(key);
        if (item == null) {
            cacheGlobal.getReadListeners().forEach(x -> {
                x.onRead(key, null);
            });
            return null;
        }
        item.setLastTime(System.currentTimeMillis());
        item.getHitCount().incrementAndGet();
        //惰性删除，判断缓存是否过期
        long timeoutTime = System.currentTimeMillis() - item.getWriteTime();
        if (item.getExpireTime() * 1000 < timeoutTime) {
            //执行过期缓存
            remove(key);
            return null;
        }
        MyCache finalItem = item;
        cacheGlobal.getReadListeners().forEach(x -> {
            x.onRead(key, finalItem);
        });
        //如果这个key要设置成读取后移除，这里需要支持下
        if(item.isDelAfterRead()) {
            remove(key);
        }
        return item.getValue();
    }

    /**
     * 是否包含缓存key
     *
     * @param key
     * @return
     */
    @Override
    public boolean containKey(String key) {
        MyCache item = cacheGlobal.cacheConcurrentHashMap.get(key);
        if (item == null) {
            return false;
        }
        //请求的时候会判断key是否过期，如果过期则进行回收
        long timeoutTime = System.currentTimeMillis() - item.getWriteTime();
        if (item.getExpireTime() * 1000 >= timeoutTime) {
            return true;
        }
        //执行过期缓存
        cacheGlobal.cacheConcurrentHashMap.remove(key);
        cacheGlobal.getRemovalListeners().forEach(x -> {
            x.onRemoval(key);
        });
        return false;
    }

    /**
     * 移除某个key
     *
     * @param key
     * @return
     */
    @Override
    public boolean remove(String key) {
        cacheGlobal.cacheConcurrentHashMap.remove(key);
        cacheGlobal.getRemovalListeners().forEach(x -> {
            x.onRemoval(key);
        });
        return true;
    }

    /**
     * 注册插入监听器
     *
     * @param addListener
     * @return
     */
    public CacheUtils registryAddListener(AddListener addListener) {
        this.cacheGlobal.addAddListener(addListener);
        return this;
    }

    /**
     * 注册缓存刷新监听器
     *
     * @param refreshListener
     * @return
     */
    public CacheUtils registryRefreshListener(RefreshListener refreshListener, int time){
        Thread refreshThread = new Thread(new RefreshThread(refreshListener,time));
        refreshThread.start();
        return this;
    }

    /**
     * 注册移除监听器
     *
     * @param removalListener
     * @return
     */
    public CacheUtils registryRemoveListener(RemovalListener removalListener) {
        this.cacheGlobal.addRemovalListener(removalListener);
        return this;
    }

    /**
     * 注册读取监听器
     *
     * @param readListener
     * @return
     */
    public CacheUtils registryReadListener(ReadListener readListener) {
        this.cacheGlobal.addReadListener(readListener);
        return this;
    }

    public int size(){
        return cacheGlobal.cacheConcurrentHashMap.size();
    }


    public CacheUtils maxmumSize(int maxmumSize){
        this.cacheGlobal.maxmumSize(maxmumSize);
        return this;
    }

    public CacheGlobal getCacheGlobal() {
        return cacheGlobal;
    }

    public void setCacheGlobal(CacheGlobal cacheGlobal) {
        this.cacheGlobal = cacheGlobal;
    }
}
