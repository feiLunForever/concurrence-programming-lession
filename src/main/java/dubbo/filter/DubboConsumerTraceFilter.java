package dubbo.filter;

import com.alibaba.fastjson.JSON;
import dubbo.constants.RequestContentConstants;
import dubbo.context.CommonRequest;
import dubbo.context.CommonRequestContext;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @Author linhao
 * @Date created in 2:33 下午 2022/7/1
 */
@Activate(group = CommonConstants.CONSUMER)
public class DubboConsumerTraceFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(DubboConsumerTraceFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Object o = CommonRequestContext.get(RequestContentConstants.COMMON_REQUEST);
        try {
            if (o != null) {
                CommonRequest commonRequest = (CommonRequest) o;
                LOGGER.info("[DubboConsumerTraceFilter] ------> commonRequest is {}",commonRequest);
                invocation.getAttachments().put(String.valueOf(RequestContentConstants.COMMON_REQUEST), JSON.toJSONString(commonRequest));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return invoker.invoke(invocation);
    }
}
