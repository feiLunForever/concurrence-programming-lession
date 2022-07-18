package 并发编程12.consumer.interceptor;

import dubbo.constants.RequestContentConstants;
import dubbo.context.CommonRequest;
import dubbo.context.CommonRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

/**
 * 渠道过滤器
 *
 * @Author idea
 * @Date created in 2:50 下午 2022/7/7
 */
public class ChannelFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        Long userId = convertUserId(httpServletRequest);
        CommonRequest commonRequest = new CommonRequest();
        commonRequest.setTraceId(UUID.randomUUID().toString());
        commonRequest.setUserId(userId);
        CommonRequestContext.put(RequestContentConstants.COMMON_REQUEST, commonRequest);
        LOGGER.info("CommonRequest is {}", commonRequest);
        filterChain.doFilter(servletRequest,servletResponse);
    }

    private static Long convertUserId(HttpServletRequest request) {
        String userIdStr = request.getHeader("user-id");
        if (userIdStr == null) {
            return -1L;
        }
        return Long.valueOf(userIdStr);
    }
}
