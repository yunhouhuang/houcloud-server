/*
 * Copyright (C) 2022 Yunhou·Huang  yunhouhuang@gmail.com.
 * All rights reserved.
 * Official Web Site: http://houcloud.com.
 */

package com.houcloud.example.model.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * GMV 图表数据
 * </p>
 *
 * @author <a href="mailto:yunhouhuang@gmail.com">yunhouhuang@gmail.com</a>
 */
@Data
public class GmvChartDataResponse {

    public record DataItem(String date,BigDecimal gmv){}

    private List<DataItem> items;

    private BigDecimal total;

}
