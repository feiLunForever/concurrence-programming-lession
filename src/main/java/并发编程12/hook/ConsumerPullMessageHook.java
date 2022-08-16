package 并发编程12.hook;

import com.alibaba.fastjson.JSON;
import dubbo.constants.RequestContentConstants;
import dubbo.context.CommonRequest;
import dubbo.context.CommonRequestContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.hook.FilterMessageContext;
import org.apache.rocketmq.client.hook.FilterMessageHook;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.Iterator;

/**
 * @Author idea
 * @Date created in 10:35 上午 2022/8/7
 */
public class ConsumerPullMessageHook implements FilterMessageHook {

    @Override
    public String hookName() {
        return "ConsumerPullMessageHook";
    }

    @Override
    public void filterMessage(FilterMessageContext filterMessageContext) {
        Iterator<MessageExt> iterator = filterMessageContext.getMsgList().iterator();
        while (iterator.hasNext()) {
            MessageExt messageExt = iterator.next();
            String traceJson = messageExt.getUserProperty(RequestContentConstants.COMMON_REQUEST.name());
            if (StringUtils.isNotEmpty(traceJson)) {
                CommonRequestContext.put(RequestContentConstants.COMMON_REQUEST,JSON.parseObject(traceJson,CommonRequest.class));
            }
        }

    }
}
