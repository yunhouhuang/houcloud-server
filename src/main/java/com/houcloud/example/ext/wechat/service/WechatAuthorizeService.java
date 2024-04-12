/*
 * Copyright (C) 2022 Yunhou·Huang  yunhouhuang@gmail.com.
 * All rights reserved.
 * Official Web Site: http://houcloud.com.
 */

package com.houcloud.example.ext.wechat.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.houcloud.example.common.exception.BusinessException;
import com.houcloud.example.ext.wechat.model.AppletAuthorizeResponse;
import com.houcloud.example.ext.wechat.utils.WxUtil;
import com.houcloud.example.ext.wechat.exception.WechatException;
import com.houcloud.example.ext.wechat.model.WechatMobileAuthBody;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import static com.houcloud.example.ext.wechat.WechatConfig.*;

/**
 * <p>
 * mark
 * </p>
 *
 * @author <a href="mailto:yunhouhuang@gmail.com">yunhouhuang@gmail.com</a>
 */
@Slf4j
@Service
public class WechatAuthorizeService {

    public AppletAuthorizeResponse authorizeCode(String code){
        String json = StrUtil.format(AuthAppletCodeUrl, WechatAppletAppId, WechatAppletAppSecret,code);
        String response = HttpUtil.get(json);
        if (StrUtil.isBlank(response)){
            throw WechatException.exception("授权码请求错误");
        }
        log.info("解析微信小程序授权码:{}",response);
        return JSONObject.parseObject(response, AppletAuthorizeResponse.class);
    }

    /**
     * 绑定手机号数据校验
     */
    public String checkBindingPhone(WechatMobileAuthBody body) {
        // 参数校验
        if (StrUtil.isBlank(body.getCode())) {
            throw BusinessException.exception("小程序获取手机号code不能为空");
        }
        if (StrUtil.isBlank(body.getEncryptedData())) {
            throw BusinessException.exception("小程序获取手机号加密数据不能为空");
        }
        if (StrUtil.isBlank(body.getVi())) {
            throw BusinessException.exception("小程序获取手机号加密算法的初始向量不能为空");
        }
        // 获取appid
        String programAppId = WechatAppletAppId;
        if (StringUtils.isBlank(programAppId)) {
            throw BusinessException.exception("微信小程序appId未设置");
        }
        AppletAuthorizeResponse response = authorizeCode(body.getCode());
        System.out.println("小程序登陆成功 = " + JSON.toJSONString(response));
        String decrypt = WxUtil.decrypt(programAppId, body.getEncryptedData(), response.getSession_key(), body.getVi());
        if (StrUtil.isBlank(decrypt)) {
            throw BusinessException.exception("微信小程序获取手机号解密失败");
        }
        JSONObject jsonObject = JSONObject.parseObject(decrypt);
        if (StrUtil.isBlank(jsonObject.getString("phoneNumber"))) {
            throw BusinessException.exception("微信小程序获取手机号没有有效的手机号");
        }
        return jsonObject.getString("phoneNumber");
    }


}
