package com.houcloud.example.common.notice.sms;

import lombok.Data;

/**
 * @author Houcloud
 * @since 2020/5/14 12:50
 */
@Data
public class SmsConfig {

    private String domain;
    private String version;
    private String key;
    private String secret;
}
