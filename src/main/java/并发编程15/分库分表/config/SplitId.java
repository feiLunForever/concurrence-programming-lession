package 并发编程15.分库分表.config;

import java.lang.annotation.*;

/**
 * @Author linhao
 * @Date created in 8:50 上午 2022/8/13
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SplitId {

    int mod() default 10;
}
