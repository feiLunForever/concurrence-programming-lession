package dubbo.filter;


import com.alibaba.fastjson.JSON;
import dubbo.constants.RequestContentConstants;
import dubbo.context.CommonRequest;
import dubbo.context.CommonRequestContext;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author linhao
 * @Date created in 3:37 下午 2022/7/7
 */
@Activate(group = CommonConstants.PROVIDER)
public class DubboProviderTraceFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(DubboProviderTraceFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        try {
            String attachment = invocation.getAttachment(String.valueOf(RequestContentConstants.COMMON_REQUEST));
            if (StringUtils.isNotEmpty(attachment)) {
                CommonRequest commonRequest = JSON.parseObject(attachment,CommonRequest.class);
                LOGGER.info("[DubboConsumerTraceFilter] ------> commonRequest is {}",commonRequest);
                CommonRequestContext.put(RequestContentConstants.COMMON_REQUEST,commonRequest);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return invoker.invoke(invocation);
    }
}
