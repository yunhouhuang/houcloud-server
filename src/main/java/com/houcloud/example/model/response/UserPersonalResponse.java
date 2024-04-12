/*
 * Copyright (C) 2022 Yunhou·Huang  yunhouhuang@gmail.com.
 * All rights reserved.
 * Official Web Site: http://houcloud.com.
 */

package com.houcloud.example.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * mark
 * </p>
 *
 * @author <a href="mailto:yunhouhuang@gmail.com">yunhouhuang@gmail.com</a>
 */
@Data
@Schema(description = "用户个人资料")
public class UserPersonalResponse {

    private Long id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "用户密码(密文)")
    private String password;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "手机号")
    private String mobile;

    @Schema(description = "余额")
    private Long wallet;

    @Schema(description = "电子邮箱")
    private String email;

    @Schema(description = "未读消息通知数")
    private Long noticeMsgNum;

    @Schema(description = "身份证号（实名信息）")
    private String idCardNo;

    @Schema(description = "真实姓名（实名信息）")
    private String realName;

}
