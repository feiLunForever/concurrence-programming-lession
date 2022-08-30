package 并发编程17.手动实现本地缓存.core;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author idea
 * @Date created in 8:48 上午 2022/8/29
 */
public class MyCache {

    private Object key;

    private Object value;

    private long lastTime;

    private long writeTime;

    private long expireTime;

    private boolean delAfterRead;

    private AtomicLong hitCount = new AtomicLong(0);

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    public long getWriteTime() {
        return writeTime;
    }

    public void setWriteTime(long writeTime) {
        this.writeTime = writeTime;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public boolean isDelAfterRead() {
        return delAfterRead;
    }

    public void setDelAfterRead(boolean delAfterRead) {
        this.delAfterRead = delAfterRead;
    }

    public AtomicLong getHitCount() {
        return hitCount;
    }

    public void setHitCount(AtomicLong hitCount) {
        this.hitCount = hitCount;
    }
}
