package 并发编程08.线程本地变量.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * @Author idea
 * @Date created in 3:22 下午 2022/6/26
 */
@Configuration
public class TimeCountInterceptor implements HandlerInterceptor {

    static class CommonThreadLocal<Long> extends ThreadLocal{
        @Override
        protected Object initialValue() {
            return -1;
        }
    }
    private static ThreadLocal<Long> timeCount = new CommonThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        System.out.println("提前赋值的获取："+timeCount.get());
        //中间写逻辑代码，比如判断是否登录成功，失败则返回false
        timeCount.set(System.currentTimeMillis());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        long currentTime = System.currentTimeMillis();
        long startTime = timeCount.get();
        long timeUse = currentTime - startTime;
        System.out.println(Thread.currentThread().getName() + "耗时：" + timeUse + "ms");
        timeCount.remove();
    }


}
