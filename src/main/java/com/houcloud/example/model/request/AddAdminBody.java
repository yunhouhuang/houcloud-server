/*
 * Copyright (C) 2022 Yunhou·Huang  yunhouhuang@gmail.com.
 * All rights reserved.
 * Official Web Site: http://houcloud.com.
 */

package com.houcloud.example.model.request;

import com.houcloud.example.model.entity.Admin;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * <p>
 * mark
 * </p>
 *
 * @author <a href="mailto:yunhouhuang@gmail.com">yunhouhuang@gmail.com</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AddAdminBody extends Admin {

    @Schema(description = "头像")
    @NotBlank(message = "头像不能为空")
    private String avatar;

    @Schema(description = "姓名")
    @NotBlank(message = "姓名不能为空")
    private String nickname;

    @Schema(description = "用户密码(密文)")
    @NotBlank(message = "密码不能为空")
    private String password;
    @NotEmpty(message = "角色不能为空")
    @Schema(description = "角色ID集合")
    private List<Long> roleIds;

}
