/*
 * Copyright (C) 2022 Yunhou·Huang  yunhouhuang@gmail.com.
 * All rights reserved.
 * Official Web Site: http://houcloud.com.
 */

package com.houcloud.example.model.request;

import com.houcloud.example.model.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
public class AddRoleBody extends Role {

    @Schema(description = "角色名称",nullable = true)
    @NotBlank(message = "名称不能为空")
    private String name;

    @Schema(description = "角色编码",nullable = true)
    @NotBlank(message = "编码不能为空")
    private String code;

    @Schema(description = "菜单ID")
    private List<Long> menuIds;
}
