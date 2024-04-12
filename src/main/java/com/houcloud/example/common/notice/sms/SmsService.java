package com.houcloud.example.common.notice.sms;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.houcloud.example.common.exception.BusinessException;
import com.houcloud.example.utils.IpUtil;
import com.houcloud.example.utils.MobileUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class SmsService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public static final String MOBILE_CACHE_KEY = "mobile:code:";
    public static final String LIMIT_KEY = "mobile:limit:";

    private String getMsgCode() {
        int n = 6;
        StringBuilder code = new StringBuilder();
        Random ran = new Random();
        for (int i = 0; i < n; i++) {
            code.append(Integer.valueOf(ran.nextInt(10)).toString());
        }
        return code.toString();
    }

    public void verifyCode(String mobile, String code) {
        verifyCodeNoDel(mobile,code);
        stringRedisTemplate.delete(MOBILE_CACHE_KEY + mobile);
    }

    public void verifyCodeNoDel(String mobile, String code) {
        String cacheCode = stringRedisTemplate.opsForValue().get(MOBILE_CACHE_KEY + mobile);
        if (StrUtil.isBlank(code)||code.length()!=6){
            throw BusinessException.exception("验证码错误");
        }if (StrUtil.isBlank(cacheCode)){
            throw BusinessException.exception("验证码已过期");
        }
        if (!code.equals(cacheCode)){
            throw BusinessException.exception("验证码错误");
        }
    }


    public void delKey(String mobile, String code) {
        stringRedisTemplate.delete(MOBILE_CACHE_KEY + mobile);
    }


    public boolean sendCode(String mobile, HttpServletRequest req) {
        MobileUtil.verifyMobile(mobile);
        Boolean has = stringRedisTemplate.hasKey(LIMIT_KEY + mobile);
        if (BooleanUtil.isTrue(has)){
            throw BusinessException.exception("请勿频繁操作");
        }
        stringRedisTemplate.opsForValue().set(LIMIT_KEY +mobile,"1",30L,TimeUnit.SECONDS);
        String realIp = IpUtil.getRealIp(req);
        DefaultProfile profile = DefaultProfile.getProfile("cn-guangzhou",
                "youraccesskey",
                "yoursecurity");//自己账号的AccessKey信息
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");//短信服务的服务接入地址
        request.setSysVersion("2017-05-25");//API的版本号
        request.setSysAction("SendSms");//API的名称
        String msgCode = getMsgCode();
        String key = IdUtil.fastSimpleUUID();
        request.putQueryParameter("PhoneNumbers", mobile);//接收短信的手机号码
        request.putQueryParameter("SignName", "HOUCLOUD");//短信签名名称
        request.putQueryParameter("TemplateCode", "SMS_0000000");//短信模板ID
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + msgCode + "\"}");//短信模板变量对应的实际值
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
//            saveLog(mobile, realIp, response.getData(), msgCode);
            stringRedisTemplate.opsForValue().set(MOBILE_CACHE_KEY+mobile,msgCode,5L, TimeUnit.MINUTES);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Async
    public void sendAsyncCode(String mobile, HttpServletRequest req) {
        sendCode(mobile, req);
    }

//    @Resource
//    private SmsLogService smsLogService;
//
//    @Async
//    public void saveLog(String mobiles, String ip, String dataJson, String content) {
//        SmsLog smsLog = new SmsLog();
//        smsLog.setMobiles(mobiles);
//        smsLog.setIp(ip);
//        smsLog.setSendResp(dataJson);
//        smsLog.setContent(content);
//        boolean save = smsLogService.save(smsLog);
//        log.info("短信日志入库状态：{}", save);
//        if (!save) {
//            log.info("短信日志入库失败:{}", smsLog);
//        }
//    }
}