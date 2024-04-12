package com.houcloud.example.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * <p>
 * 价格转换工具
 * </p>
 *
 * @author Houcloud
 * @since 2021/9/30
 */
public class PriceCovertUtil {

    /**
     * 分转小数点元
     *
     * @param val
     * @return
     */
    public static BigDecimal redCentLongToBigDecimal(Long val) {
        if (Objects.nonNull(val) && val != 0) {
            return new BigDecimal(val).divide(new BigDecimal(100), 2, RoundingMode.CEILING);
        }
        return new BigDecimal("0.00");
    }

    /**
     * 转分
     *
     * @param val
     * @return
     */
    public static Long redCentBigDecimalToLong(BigDecimal val) {
        if (Objects.isNull(val)) {
            return 0L;
        }
        BigDecimal p = val.setScale(2, RoundingMode.DOWN);
        BigDecimal multiply = p.multiply(new BigDecimal(100));
        return multiply.longValue();
    }
}
