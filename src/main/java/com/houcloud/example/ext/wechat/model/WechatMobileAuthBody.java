package com.houcloud.example.ext.wechat.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@Accessors(chain = true)
@Schema( description="微信绑定手机号请求对象")
public class WechatMobileAuthBody implements Serializable {

    @Serial
    private static final long serialVersionUID=1L;

    @Schema(description = "新用户登录时返回的key")
    private String key;

    @Schema(description = "小程序获取手机号加密数据")
    @NotBlank(message = "小程序获取手机号加密数据不能为空")
    private String encryptedData;

    @Schema(description = "加密算法的初始向量")
    @NotBlank(message = "加密算法的初始向量不能为空")
    private String vi;

    @Schema(description = "小程序code")
    @NotBlank(message = "小程序code不能为空")
    private String code;
}