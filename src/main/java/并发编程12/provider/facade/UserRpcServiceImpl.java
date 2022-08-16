package 并发编程12.provider.facade;

import dubbo.constants.RequestContentConstants;
import dubbo.context.CommonRequest;
import dubbo.context.CommonRequestContext;
import dubbo.interfaces.UserRpcService;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import 并发编程12.constants.TopicConstants;

import javax.annotation.Resource;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


/**
 * @Author idea
 * @Date created in 7:37 下午 2022/7/5
 */
@DubboService
public class UserRpcServiceImpl implements UserRpcService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRpcServiceImpl.class);

    public Executor executor = Executors.newSingleThreadExecutor();

    @Resource
    private DefaultMQProducer mqProducer;

    @Override
    public boolean isUserExist(long id) {
        LOGGER.info("[UserRpcService] isUserExist is " + true);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Object o = CommonRequestContext.get(RequestContentConstants.COMMON_REQUEST);
                if (o == null) {
                    LOGGER.error("o is null");
                } else {
                    CommonRequest commonRequest = (CommonRequest) o;
                    LOGGER.info("this is async thread! commonRequest is " + commonRequest);
                }
            }
        });
        try {
            mqProducer.send(new Message(TopicConstants.TEST_TOPIC,"test-mq".getBytes()));
            System.out.println("发送消息数据");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

}
