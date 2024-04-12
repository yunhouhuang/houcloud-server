/*
 * Copyright (C) 2022 Yunhou·Huang  yunhouhuang@gmail.com.
 * All rights reserved.
 * Official Web Site: http://houcloud.com.
 */

package com.houcloud.example.model.ops;

import lombok.Data;

/**
 * <p>
 * cpu 参数
 * </p>
 *
 * @author <a href="mailto:yunhouhuang@gmail.com">yunhouhuang@gmail.com</a>
 */
@Data
public class CpuInfo {

    private Integer coreNum;
    private double usedRate;
    private double freeRate;

}
