package 并发编程16.分布式锁核心实现.lock;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * @Author idea
 * @Date created in 6:06 下午 2022/8/27
 */
public class LockStatusThreadLock extends TransmittableThreadLocal<Boolean> {

    @Override
    protected Boolean initialValue() {
        return false;
    }

}
