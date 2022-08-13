package 并发编程15.分库分表.config;

import com.baomidou.mybatisplus.extension.plugins.handler.TableNameHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.DynamicTableNameInnerInterceptor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author linhao
 * @Date created in 11:53 下午 2022/8/9
 */
//@Configuration
public class TableNameConfig {

    @Resource
    private ApplicationContext applicationContext;

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        DynamicTableNameInnerInterceptor dynamicTableNameInnerInterceptor = new DynamicTableNameInnerInterceptor();
        Map<String, TableNameHandler> map = new HashMap<String,TableNameHandler>();
        map.put("t_user_message",new IdModTableNameParser(1000));
        dynamicTableNameInnerInterceptor.setTableNameHandlerMap(map);
        mybatisPlusInterceptor.addInnerInterceptor(new SqlSplitInterceptor(applicationContext));
        mybatisPlusInterceptor.addInnerInterceptor(dynamicTableNameInnerInterceptor);
        return mybatisPlusInterceptor;
    }

}
