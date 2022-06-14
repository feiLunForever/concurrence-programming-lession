package 并发编程01.守护线程;

/**
 * @Author linhao
 * @Date created in 6:45 下午 2022/5/18
 */
public class DaemonThreadDemo {

    public static void main(String[] args) throws InterruptedException {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("jvm exit success!! ")));

        Thread testThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(2000);
                    System.out.println("thread still running ....");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        testThread.setDaemon(true);
        testThread.start();
    }
}
