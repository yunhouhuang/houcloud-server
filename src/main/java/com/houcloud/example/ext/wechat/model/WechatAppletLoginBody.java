/*
 * Copyright (C) 2022 Yunhou·Huang  yunhouhuang@gmail.com.
 * All rights reserved.
 * Official Web Site: http://houcloud.com.
 */

package com.houcloud.example.ext.wechat.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * <p>
 * 小程序授权登录请求体
 * </p>
 *
 * @author <a href="mailto:yunhouhuang@gmail.com">yunhouhuang@gmail.com</a>
 */
@Data
public class WechatAppletLoginBody {

    @NotBlank(message = "授权码不能为空")
    private String code;
    private String cloudID;
    private String encryptedData;
    private String errMsg;
    private String rawData;
    private String signature;
    private String vi;
    private Long invite_id;
    private String sync_info;
    private UserInfo userInfo;
}
