package 并发编程16.分布式锁核心实现.config;

/**
 * @Author idea
 * @Date created in 5:48 下午 2022/8/27
 */
public class LockInfo {

    private String lockKey;

    public String getLockKey() {
        return lockKey;
    }

    public void setLockKey(String lockKey) {
        this.lockKey = lockKey;
    }

}
