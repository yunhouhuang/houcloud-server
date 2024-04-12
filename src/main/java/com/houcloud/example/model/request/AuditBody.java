/*
 * Copyright (C) 2022 Yunhou·Huang  yunhouhuang@gmail.com.
 * All rights reserved.
 * Official Web Site: http://houcloud.com.
 */

package com.houcloud.example.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * <p>
 * 审核通用参数
 * </p>
 *
 * @author <a href="mailto:yunhouhuang@gmail.com">yunhouhuang@gmail.com</a>
 */
@Data
@Schema(description = "审核通用参数")
public class AuditBody {
    @Schema(description = "是否通过")
    @NotNull(message = "id不能为空")
    private Long id;
    @Schema(description = "是否通过")
    @NotNull(message = "请指定是否通过")
    private Boolean pass;
    @Schema(description = "审核备注，不通过时必填")
    private String remarks;
}
