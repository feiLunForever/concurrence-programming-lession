package 并发编程15.分库分表.config;

import java.lang.annotation.*;

/**
 * @Author linhao
 * @Date created in 9:12 上午 2022/8/13
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SplitTable {

    Class clazz() default Object.class;
}
