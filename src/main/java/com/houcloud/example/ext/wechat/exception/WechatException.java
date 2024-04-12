/*
 * Copyright (C) 2022 Yunhou·Huang  yunhouhuang@gmail.com.
 * All rights reserved.
 * Official Web Site: http://houcloud.com.
 */

package com.houcloud.example.ext.wechat.exception;

import com.houcloud.example.common.exception.BusinessStatus;

import java.io.Serial;

/**
 * <p>
 * 微信业务异常
 * </p>
 *
 * @author <a href="mailto:yunhouhuang@gmail.com">yunhouhuang@gmail.com</a>
 */
public class WechatException extends RuntimeException{


    @Serial
    private static final long serialVersionUID = 3034121940056795549L;

    private Integer code;

    public WechatException() {

    }

    public WechatException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public WechatException(BusinessStatus status) {
        super(status.getMessage());
        this.code = status.getCode();
    }

    public static WechatException exception(String message) {
        return new WechatException(message, BusinessStatus.ERROR.getCode());
    }

    public static WechatException exception(int code, String message) {
        return new WechatException(message, code);
    }

    public static WechatException exception(BusinessStatus businessStatus) {
        return new WechatException(businessStatus.getMessage(), businessStatus.getCode());
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
