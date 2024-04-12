/*
 * Copyright (C) 2022 Yunhou·Huang  yunhouhuang@gmail.com.
 * All rights reserved.
 * Official Web Site: http://houcloud.com.
 */

package com.houcloud.example.ext.wechat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * <p>
 * 授权码校验响应结果
 * </p>
 *
 * @author <a href="mailto:yunhouhuang@gmail.com">yunhouhuang@gmail.com</a>
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class AppletAuthorizeResponse {
    private String session_key;
    private String openid;
    private String union_id;
    private String err_code;
    private String err_msg;
}