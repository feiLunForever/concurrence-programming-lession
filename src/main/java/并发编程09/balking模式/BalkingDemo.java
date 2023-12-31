package 并发编程09.balking模式;

/**
 * @Author idea
 * @Date created in 6:57 下午 2022/5/17
 */
public class BalkingDemo {

    private volatile boolean isChange = false;

    public void saveFile() {
            if (isChange) {
                //将缓冲区的代码写入到磁盘中
                System.out.println("保存磁盘");
                isChange = false;
            }
    }

    public void changeFile() {
            isChange = true;
            //修改缓冲区的数据
            System.out.println("修改缓冲数据");
    }

    public static void main(String[] args) throws InterruptedException {
        BalkingDemo balkingDemo = new BalkingDemo();
        for(int i=0;i<10;i++){
            Thread saveThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    balkingDemo.saveFile();
                }
            });
            Thread changeThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    balkingDemo.changeFile();
                }
            });
            saveThread.start();
            changeThread.start();
            Thread.sleep(1000);
        }

    }
}
