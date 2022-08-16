package 并发编程12.hook;

import com.alibaba.fastjson.JSON;
import dubbo.constants.RequestContentConstants;
import dubbo.context.CommonRequest;
import dubbo.context.CommonRequestContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.hook.SendMessageContext;
import org.apache.rocketmq.client.hook.SendMessageHook;


/**
 * @Author idea
 * @Date created in 10:25 上午 2022/8/7
 */
public class ProducerSendMessageHook implements SendMessageHook {


    @Override
    public String hookName() {
        return "CommonMessageHook";
    }

    @Override
    public void sendMessageBefore(SendMessageContext sendMessageContext) {
        CommonRequest commonRequest = (CommonRequest) CommonRequestContext.get(RequestContentConstants.COMMON_REQUEST);
        if(StringUtils.isNotEmpty(commonRequest.getTraceId())){
            sendMessageContext.getMessage().putUserProperty(RequestContentConstants.COMMON_REQUEST.name(), JSON.toJSONString(commonRequest));
        }
    }

    @Override
    public void sendMessageAfter(SendMessageContext sendMessageContext) {
    }
}
