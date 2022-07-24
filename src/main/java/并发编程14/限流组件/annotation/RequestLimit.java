package 并发编程14.限流组件.annotation;

import java.lang.annotation.*;

/**
 * @Author linhao
 * @Date created in 3:44 下午 2022/7/24
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestLimit {


    /**
     * 并发处理次数
     *
     * @return
     */
    int limit() default 1;

    /**
     * 超时设置
     *
     * @return
     */
    int timeOut() default 3000;

    /**
     * 限流场景的名字
     *
     * @return
     */
    String name() default "";


}
