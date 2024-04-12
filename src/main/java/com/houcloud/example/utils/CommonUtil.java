/*
 * Copyright (C) 2022 Yunhou·Huang  yunhouhuang@gmail.com.
 * All rights reserved.
 * Official Web Site: http://houcloud.com.
 */

package com.houcloud.example.utils;

import java.util.UUID;

/**
 * <p>
 * mark
 * </p>
 *
 * @author <a href="mailto:yunhouhuang@gmail.com">yunhouhuang@gmail.com</a>
 */
public class CommonUtil {
    public static String getUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static Integer randomCount(Integer start, Integer end) {
        return (int) (Math.random() * (end - start + 1) + start);
    }

}
