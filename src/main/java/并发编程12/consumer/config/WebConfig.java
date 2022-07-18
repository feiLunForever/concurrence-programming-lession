package 并发编程12.consumer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import 并发编程12.consumer.interceptor.ChannelFilter;

/**
 * @Author idea
 * @Date created in 5:35 下午 2022/7/7
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public ChannelFilter channelFilter(){
        return new ChannelFilter();
    }

}
