package 并发编程14.限流组件.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import 并发编程14.限流组件.annotation.RequestLimit;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author linhao
 * @Date created in 3:48 下午 2022/7/24
 */
@Aspect
@Component
public class RequestLimitAop {

    private static Map<String, Semaphore> limitMap = new ConcurrentHashMap<>();

    @Pointcut(value = "@annotation(并发编程14.限流组件.annotation.RequestLimit)")
    public void pointCut() {

    }

    @Around(value = "pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Semaphore reqTimes = null;
        RequestLimit requestLimit = null;
        boolean isProcess = false;
        requestLimit = this.getRequestLimitInfo(joinPoint);
        if (requestLimit != null) {
            try {
                reqTimes = limitMap.get(requestLimit.name());
                if (reqTimes == null) {
                    reqTimes = new Semaphore(requestLimit.limit());
                    limitMap.put(requestLimit.name(), reqTimes);
                }
                if (reqTimes.tryAcquire()) {
                    limitMap.put(requestLimit.name(), reqTimes);
                    Object result = joinPoint.proceed();
                    isProcess = true;
                    return result;
                } else {
                    throw new IllegalAccessException("并发访问量超过预期,预期并发数：" + requestLimit.limit());
                }
            } catch (Exception e) {
                throw e;
            } finally {
                if (isProcess) {
                    reqTimes.release();
                    limitMap.put(requestLimit.name(), reqTimes);
                }
            }
        }
        return joinPoint.proceed();
    }

    public RequestLimit getRequestLimitInfo(ProceedingJoinPoint joinPoint) throws ClassNotFoundException {
        // 获取切入点的目标类
        String targetName = joinPoint.getTarget().getClass().getName();
        Class<?> targetClass = Class.forName(targetName);
        // 获取切入方法名
        String methodName = joinPoint.getSignature().getName();
        // 获取切入方法参数
        Object[] arguments = joinPoint.getArgs();
        // 获取目标类的所有方法
        Method[] methods = targetClass.getMethods();
        for (Method method : methods) {
            // 方法名相同、包含目标注解、方法参数个数相同（避免有重载）
            if (method.getName().equals(methodName) && method.isAnnotationPresent(RequestLimit.class)
                    && method.getParameterTypes().length == arguments.length) {
                return method.getAnnotation(RequestLimit.class);
            }
        }
        return null;
    }
}
