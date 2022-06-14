package 并发编程00.cpu调度;

/**
 * @Author linhao
 * @Date created in 11:21 下午 2022/6/13
 */
public class CpuTaskDemo {

    public void printChar(String word){
        System.out.print(word);
    }

    public static void main(String[] args) {
        CpuTaskDemo c = new CpuTaskDemo();
        Thread taskA = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<100;i++){
                    c.printChar("A");
                }
            }
        });
        taskA.start();

        Thread taskB = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<100;i++){
                    c.printChar("B");
                }
            }
        });
        taskB.start();

        Thread taskC = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<100;i++){
                    c.printChar("C");
                }
            }
        });
        taskC.start();

        Thread taskD = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<100;i++){
                    c.printChar("D");
                }
            }
        });
        taskD.start();
    }
}
