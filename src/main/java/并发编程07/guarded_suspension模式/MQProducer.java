package 并发编程07.guarded_suspension模式;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Author linhao
 * @Date created in 5:35 下午 2022/5/17
 */
public class MQProducer {

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    /**
     * 发送消息给到broker节点
     *
     * @param content
     * @param topic
     * @return
     */
    public SendResult sendMsg(String content, String topic) {
        this.sendMsgToBroker(content, topic);
        try {
            boolean countDownStatus = countDownLatch.await(3, TimeUnit.SECONDS);
            if(countDownStatus){
                return SendResult.SUCCESS;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return SendResult.FAIL;
    }

    /**
     * 当broker返回消息成功写入后回调这里
     */
    private void receiveBrokerMsg(String msg){
        //处理broker返回的心跳包
        countDownLatch.countDown();
        System.out.println("countDown");
    }

    /**
     * 模拟发送消息到broker节点
     *
     * @param content
     * @param topic
     */
    private void sendMsgToBroker(String content, String topic) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        final MQProducer mqProducer = new MQProducer();
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mqProducer.receiveBrokerMsg("");
            }
        });
        t.start();
        SendResult sendResult = mqProducer.sendMsg("test","test-topic");
        System.out.println(sendResult);
    }

}
