package 并发编程03.sync锁;

/**
 * @Author idea
 * @Date created in 8:08 上午 2022/5/22
 */
public class StockNumSale {

    //车票剩余数目
    private int stockNum;


    public StockNumSale(int stockNum) {
        this.stockNum = stockNum;
    }

    /**
     * 锁定库存
     *
     * @return
     */
    public boolean lockStock(int num) {
        synchronized(this){
//            if (!isStockEnough()) {
//                return false;
//            }
            for (int i = 0; i < num; i++) {
                stockNum--;
            }
            return true;
        }
    }

    private boolean isStockEnough() {
        return stockNum > 0;
    }


    public void printStockNum() {
        if (this.stockNum < 0) {
            System.out.println("库存不足：" + this.stockNum);
        }
    }

//    public static void batchTest(int threadNum, int stockNum) {
//        CountDownLatch begin = new CountDownLatch(1);
//        CountDownLatch end = new CountDownLatch(threadNum);
//        StockNumSale stockNumSale = new StockNumSale(stockNum);
//        for (int i = 0; i < threadNum; i++) {
//            Thread t = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        //等待，模拟并发
//                        begin.await();
//                        stockNumSale.lockStock(100);
//                        end.countDown();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//            t.start();
//        }
//        try {
//            begin.countDown();
//            end.await();
//            stockNumSale.printStockNum();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

//    public static void main(String[] args) {
//        batchTest(200, 1000);
//    }
}
