package com.houcloud.example.common.notice.dingtalk;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Houcloud
 * @since 2020/5/14 12:51
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "message.dingtalk")
public class DingTalkConfig {
    /**
     * 通知地址
     */
    private String hooks;

    /**
     * 是否开启通知
     */
    private Boolean enabled = false;
}
