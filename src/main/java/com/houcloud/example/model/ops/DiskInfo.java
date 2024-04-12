/*
 * Copyright (C) 2022 Yunhou·Huang  yunhouhuang@gmail.com.
 * All rights reserved.
 * Official Web Site: http://houcloud.com.
 */

package com.houcloud.example.model.ops;

import lombok.Data;

/**
 * <p>
 * mark
 * </p>
 *
 * @author <a href="mailto:yunhouhuang@gmail.com">yunhouhuang@gmail.com</a>
 */
@Data
public class DiskInfo {

    private String dirName;
    private String sysTypeName;
    private String typeName;
    private String total;
    private String free;
    private String used;
    private double usage;

}
