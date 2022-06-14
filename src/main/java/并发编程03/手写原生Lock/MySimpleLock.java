package 并发编程03.手写原生Lock;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @Author linhao
 * @Date created in 9:56 下午 2022/6/9
 */
public class MySimpleLock implements ILock {

    private static Unsafe unsafe;
    private static long stateOffset;
    /**
     * 锁的本质 就是在更新这个state状态
     */
    private volatile int state;

    private final Sync sync;

    public final int getState() {
        return state;
    }

    static {
        Field field = null;
        try {
            field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
            stateOffset = unsafe.objectFieldOffset
                    (MySimpleLock.class.getDeclaredField("state"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 对state字段的更新是通过cas的方式去触发
     *
     * @param expect
     * @param update
     * @return
     */
    protected final boolean compareAndSetState(int expect, int update) {
        // See below for intrinsics setup to support this
        return unsafe.compareAndSwapInt(this, stateOffset, expect, update);
    }


    public MySimpleLock() {
        sync = new FairSync();
    }

    @Override
    public void lock() {

    }

    @Override
    public void unlock() {

    }

    class FairSync extends Sync {

        @Override
        void lock() {

        }

        @Override
        protected boolean tryAcquire(int arg) {
            Thread thread = Thread.currentThread();
//            if (getState() ==)
                return true;
        }
    }

    abstract static class Sync extends AbstractQueuedSynchronizer {

        abstract void lock();

    }
}
