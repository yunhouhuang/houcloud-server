package com.houcloud.example.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

public class IDUtil {
    final static Snowflake snowflake = IdUtil.getSnowflake(1, 1);

    public static String SID() {
        return snowflake.nextIdStr();
    }

    public static Long LID() {
        return snowflake.nextId();
    }
}
