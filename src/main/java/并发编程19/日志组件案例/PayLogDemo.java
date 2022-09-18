package 并发编程19.日志组件案例;

/**
 * 一个简单的日志记录案例
 * @Author idea
 * @Date created in 10:12 上午 2022/9/4
 */
public class PayLogDemo {

    private static Logger log = new Logger();

    public void payNotify(PayNotifyVO param){
        try {
            log.info("payNotify begin,param is {}",param);
            //执行业务逻辑 ....
            log.info("payNofity success,param is {}",param);
        } catch (Exception e){
            log.error("payNotify end,param is {},error is ",param,e);
        }
    }

    public static void main(String[] args) {
        PayLogDemo payLogDemo = new PayLogDemo();
        PayNotifyVO payNotifyVO = new PayNotifyVO();
        payNotifyVO.setId("199123");
        payLogDemo.payNotify(payNotifyVO);
    }
}
