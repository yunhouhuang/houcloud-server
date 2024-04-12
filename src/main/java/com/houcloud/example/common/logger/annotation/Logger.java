package com.houcloud.example.common.logger.annotation;

import java.lang.annotation.*;

/**
 * <p> 日志注解 </p>
 *
 * @author <a href="mailto:yunhouhuang@gmail.com">yunhouhuang@gmail.com</a>
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Logger {

    /**
     * 日志描述
     *
     * @return {String}
     */
    String value();

    /**
     * 开启控制台打印
     *
     * @return {Boolean}
     */
    boolean console() default false;
}
