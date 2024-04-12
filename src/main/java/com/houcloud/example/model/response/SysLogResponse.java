/*
 * Copyright (C) 2022 Yunhou·Huang  yunhouhuang@gmail.com.
 * All rights reserved.
 * Official Web Site: http://houcloud.com.
 */

package com.houcloud.example.model.response;

import com.houcloud.example.model.entity.Admin;
import com.houcloud.example.model.entity.SysLog;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * mark
 * </p>
 *
 * @author <a href="mailto:yunhouhuang@gmail.com">yunhouhuang@gmail.com</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "系统日志")
public class SysLogResponse extends SysLog {
    @Schema(description = "操作人员")
    private Admin admin;
}
