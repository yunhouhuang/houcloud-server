package com.houcloud.example.utils;

import cn.hutool.captcha.GifCaptcha;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import com.google.common.collect.Maps;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 图片验证码工具
 * </p>
 *
 * @author Houcloud
 * @since 2021/9/14
 */
@Slf4j
@Component
public class ImgCodeUtil {

    private static StringRedisTemplate stringRedisTemplate;

    public static boolean checkPzzCode(String code) {
        // 拼图验证
        CaptchaService captchaService = SpringContextHolder.getBean(CaptchaService.class);
        CaptchaVO vo = new CaptchaVO();
        vo.setCaptchaVerification(code);
        return captchaService.verification(vo).isSuccess();
    }

    /**
     * captcha 验证码
     *
     * @return base64 验证码 + 验证码key
     */
    public static HashMap<Object, Object> imageCode() {
        // 算术类型
        GifCaptcha captcha = new GifCaptcha(100, 30);
        // 几位数运算，默认是两位
        String result = captcha.getCode();
        String key = UUID.randomUUID().toString();
        stringRedisTemplate.opsForValue().set("IMG-CODE" + key, result, 1, TimeUnit.MINUTES);
        HashMap<Object, Object> map = Maps.newHashMap();
        map.put("key", key);
        map.put("img", captcha.getImageBase64());
        if (log.isDebugEnabled()) {
            log.info("验证码 [ {} ] \t  KEY [ {} ]", result, key);
        }
        return map;
    }

    @Resource
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        ImgCodeUtil.stringRedisTemplate = stringRedisTemplate;
    }
}
