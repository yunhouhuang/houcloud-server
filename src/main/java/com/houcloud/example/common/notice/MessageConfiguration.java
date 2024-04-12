package com.houcloud.example.common.notice;

import com.houcloud.example.common.notice.dingtalk.DingTalkConfig;
import com.houcloud.example.common.notice.sms.SmsConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <p> 消息配置 <p/>
 *
 * @author Houcloud
 * @since 2020/4/21 15:28
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "message")
public class MessageConfiguration {
    private SmsConfig sms;
    private DingTalkConfig dingtalk;

}
