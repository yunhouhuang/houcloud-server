package com.houcloud.example.common.logger.config;


import com.houcloud.example.common.logger.aspect.LogRecordAspect;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 日志自动配置
 *
 * @author Houcloud
 */
@EnableAsync
@Configuration
@AllArgsConstructor
@ConditionalOnWebApplication
public class LogAutoConfiguration {

    @Bean
    public LogRecordAspect sysLogAspect(ApplicationEventPublisher publisher) {
        return new LogRecordAspect(publisher);
    }
}
