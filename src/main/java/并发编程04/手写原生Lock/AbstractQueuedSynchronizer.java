package 并发编程04.手写原生Lock;


/**
 * @Author linhao
 * @Date created in 5:29 下午 2022/6/9
 */
public class AbstractQueuedSynchronizer {

    private transient volatile Node head;
    private transient volatile Node tail;

    public void addWaiter() {
        if (head == null && tail == null) {
            tail = new Node();
            head = tail;
        }
        Node t = tail;
        Node newNode = new Node(Thread.currentThread(), t);
        t.next = newNode;
        newNode.prev = t;
        tail = newNode;
    }

    public void display() {
        Node t = head;
        while (t != null) {
            System.out.println(t.getThread().getName());
            t = t.next;
        }
    }

    public static void main(String[] args) {
        AbstractQueuedSynchronizer aqs = new AbstractQueuedSynchronizer();
        for (int i = 0; i < 10; i++) {
            aqs.addWaiter();
        }
        aqs.display();
    }

    /**
     * 留给子类去实现
     *
     * @param arg
     * @return
     */
    protected boolean tryAcquire(int arg) {
        throw new UnsupportedOperationException();
    }

    /**
     * 留给子类去实现
     *
     * @param arg
     * @return
     */
    protected boolean tryRelease(int arg) {
        throw new UnsupportedOperationException();
    }
//
//    static {
//        try {
//            Field field = Unsafe.class.getDeclaredField("theUnsafe");
//            field.setAccessible(true);
//            UNSAFE = (Unsafe)field.get(null);
//
//            headOffset = UNSAFE.objectFieldOffset
//                    (AbstractQueuedSynchronizer.class.getDeclaredField("head"));
//            tailOffset = UNSAFE.objectFieldOffset
//                    (AbstractQueuedSynchronizer.class.getDeclaredField("tail"));
//        } catch (Exception ex) {
//            throw new Error(ex);
//        }
//    }
//
//    private final boolean compareAndSetTail(Node expect, Node update) {
//        return UNSAFE.compareAndSwapObject(this, tailOffset, expect, update);
//    }
//
//    private final boolean compareAndSetHead(Node expect, Node update) {
//        return UNSAFE.compareAndSwapObject(this, headOffset, expect, update);
//    }


    static final class Node {
        private volatile Thread thread;
        private volatile Node prev;
        private volatile Node next;
        private volatile int waitStatus;

        public Node() {
        }

        public Node(Thread thread, Node prev) {
            this.thread = thread;
            this.prev = prev;
        }

        public Thread getThread() {
            return thread;
        }

        public void setThread(Thread thread) {
            this.thread = thread;
        }

        public Node getPrev() {
            return prev;
        }

        public void setPrev(Node prev) {
            this.prev = prev;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public int getWaitStatus() {
            return waitStatus;
        }

        public void setWaitStatus(int waitStatus) {
            this.waitStatus = waitStatus;
        }
    }
}
