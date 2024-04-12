/*
 * Copyright (C) 2022 Yunhou·Huang  yunhouhuang@gmail.com.
 * All rights reserved.
 * Official Web Site: http://houcloud.com.
 */

package com.houcloud.example.utils;

import cn.hutool.core.util.NumberUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * <p>
 * mark
 * </p>
 *
 * @author <a href="mailto:yunhouhuang@gmail.com">yunhouhuang@gmail.com</a>
 */
public class PriceUtil {

    private static final Long ZERO = 0L;

    /**
     * 计算佣金比  （佣金/商品价格）
     * @param cosFee 佣金
     * @param price 商品价格
     * @return ?%
     */
    public static BigDecimal getCommissionRate(Long cosFee, Long price) {
        BigDecimal result;
        if (null == cosFee || cosFee.intValue() == ZERO) {
            return BigDecimal.ZERO;
        } else {
            if (null == price || price.equals(ZERO)) {
                return  BigDecimal.ZERO;
            }
            result = BigDecimal.valueOf(cosFee).divide(BigDecimal.valueOf(price),2, RoundingMode.CEILING);
        }
        return (result.multiply(BigDecimal.valueOf(100)));
    }

    /**
     * 根据佣金比算佣金  （佣金/商品价格）
     * @param comRate 佣金比
     * @param price 商品价格
     * @return 佣金（分）
     */
    public static Long computeCosFee(BigDecimal comRate, Long price) {
        if (Objects.isNull(comRate)|| BigDecimal.ZERO.equals(comRate)){
            return  ZERO;
        }
        // 计算佣金信息
        BigDecimal div = NumberUtil.div(comRate, BigDecimal.valueOf(100));
        return NumberUtil.mul(div,price).setScale(2,RoundingMode.CEILING).longValue();
    }

    public static void main(String[] args) {
        System.out.println(computeCosFee(BigDecimal.valueOf(13.55), 1000L));
        System.out.println(getCommissionRate(10L, 1000L));
    }
}
