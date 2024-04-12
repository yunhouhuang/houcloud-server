/*
 * Copyright (C) 2022 Yunhou·Huang  yunhouhuang@gmail.com.
 * All rights reserved.
 * Official Web Site: http://houcloud.com.
 */

package com.houcloud.example.model.ops;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * mark
 * </p>
 *
 * @author <a href="mailto:yunhouhuang@gmail.com">yunhouhuang@gmail.com</a>
 */
@Data
public class CacheInfo {

    @Schema(description = "redis键值对信息")
    private Object info;
    @Schema(description = "Key数量")
    private Object dbSize;
    @Schema(description = "指令统计")
    private List<Map<String, String>> commandList;

}
