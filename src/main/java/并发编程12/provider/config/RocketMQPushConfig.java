package 并发编程12.provider.config;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.impl.producer.DefaultMQProducerImpl;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import 并发编程12.hook.ProducerSendMessageHook;

/**
 * @Author idea
 * @Date created in 7:55 下午 2022/8/16
 */
@Configuration
public class RocketMQPushConfig {

    @Bean
    public DefaultMQProducer mqProducer(){
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        producer.setNamesrvAddr("localhost:9876");

        DefaultMQProducerImpl defaultMQProducerImpl = producer.getDefaultMQProducerImpl();
        defaultMQProducerImpl.registerSendMessageHook(new ProducerSendMessageHook());
        try {
            defaultMQProducerImpl.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        return producer;
    }

}
