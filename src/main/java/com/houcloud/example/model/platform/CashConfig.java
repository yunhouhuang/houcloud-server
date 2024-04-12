/*
 * Copyright (C) 2022 Yunhou·Huang  yunhouhuang@gmail.com.
 * All rights reserved.
 * Official Web Site: http://houcloud.com.
 */

package com.houcloud.example.model.platform;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * 提现的配置信息
 * </p>
 *
 * @author <a href="mailto:yunhouhuang@gmail.com">yunhouhuang@gmail.com</a>
 */
@Data
public class CashConfig {
    @Schema(description = "每次提现下限(CNY 分)")
    private Long minCash;
    @Schema(description = "每次提现上限(CNY 分)")
    private Long maxCash;
    @Schema(description = "多少天提现一次")
    private Integer cashDays;
    @Schema(description = "提现费率 x%")
    private BigDecimal serviceRatio;
    @Schema(description = "提现说明")
    private String remark;
}
