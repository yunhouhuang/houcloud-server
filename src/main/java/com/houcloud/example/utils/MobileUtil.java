package com.houcloud.example.utils;


import cn.hutool.core.util.StrUtil;
import com.houcloud.example.common.exception.BusinessException;

public class MobileUtil {
    /**
     * todo 号码脱敏
     *
     * @param mobile
     * @return
     */
    public static String decodeMobile(String mobile) {
        if (!isMobile(mobile)) {
            return mobile;
        }
        StringBuilder decode = new StringBuilder();
        for (int i = 0; i < mobile.toCharArray().length; i++) {
            if (i > 2 && i < 7) {
                decode.append("*");
                continue;
            }
            decode.append(mobile.toCharArray()[i]);
        }
        return decode.toString();
    }

    /**
     * 判断手机号是否合法
     *
     * @param mobile 手机号
     * @return 是否合法
     */
    public static boolean isMobile(String mobile) {
        return StrUtil.isNotBlank(mobile) && mobile.matches("^1[345789]\\d{9}$");
    }

    /**
     * 不是手机号会报异常
     *
     * @param mobile 手机号
     */
    public static void verifyMobile(String mobile) {
        if (!isMobile(mobile)) {
            throw BusinessException.exception("手机号码格式错误");
        }
    }
}