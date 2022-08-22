package 并发编程15.分库分表.config;

import com.baomidou.mybatisplus.extension.plugins.handler.TableNameHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.DynamicTableNameInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;

import java.util.HashMap;
import java.util.Map;

import static 并发编程15.分库分表.config.TableEnum.T_USER_MESSAGE;

/**
 * @Author linhao
 * @Date created in 11:53 下午 2022/8/9
 */
@Configuration
public class TableNameConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        DynamicTableNameInnerInterceptor dynamicTableNameInnerInterceptor = new DynamicTableNameInnerInterceptor();
        Map<String, TableNameHandler> map = new HashMap<>();
        map.put(T_USER_MESSAGE.getName(),new IdModTableNameParser(T_USER_MESSAGE.getMod()));
        dynamicTableNameInnerInterceptor.setTableNameHandlerMap(map);
        mybatisPlusInterceptor.addInnerInterceptor(dynamicTableNameInnerInterceptor);
        return mybatisPlusInterceptor;
    }

}
