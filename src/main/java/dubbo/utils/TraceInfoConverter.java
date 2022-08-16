package dubbo.utils;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import dubbo.constants.RequestContentConstants;
import dubbo.context.CommonRequest;
import dubbo.context.CommonRequestContext;

/**
 * @Author idea
 * @Date created in 10:35 下午 2022/7/18
 */
public class TraceInfoConverter extends ClassicConverter {


    @Override
    public String convert(ILoggingEvent iLoggingEvent) {
        CommonRequest request = (CommonRequest) CommonRequestContext.get(RequestContentConstants.COMMON_REQUEST);
        return request == null ? null : convertFromRequest(request);
    }

    /**
     * 格式化日志
     *
     * @param request
     * @return
     */
    public String convertFromRequest(CommonRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append("traceId:").append(request.getTraceId())
                .append(",").append("userId:").append(request.getUserId());
        return builder.toString();
    }
}
