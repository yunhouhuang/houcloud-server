/*
 * Copyright (C) 2022 Yunhou·Huang  yunhouhuang@gmail.com.
 * All rights reserved.
 * Official Web Site: http://houcloud.com.
 */

package com.houcloud.example.controller.front;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.houcloud.example.common.result.Result;
import com.houcloud.example.model.platform.PlatformInfo;
import com.houcloud.example.service.ISysConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 平台信息
 * </p>
 *
 * @author <a href="mailto:yunhouhuang@gmail.com">yunhouhuang@gmail.com</a>
 */

@Slf4j
@RestController
@Tag(name = "平台信息")
@RequestMapping("/api/front/platform")
public class PlatformInfoController {

    @Resource
    private ISysConfigService sysConfigService;
    @GetMapping
    @Operation(summary = "获取平台信息")
    public Result<PlatformInfo> getPlatformInfo(){
        String json = sysConfigService.getValueByKey("platform-info");
        if (StrUtil.isBlank(json)){
            return Result.success();
        }
        PlatformInfo platformInfo = JSONUtil.toBean(json, PlatformInfo.class);
        return Result.success(platformInfo);
    }

    @GetMapping("/agreement")
    @Operation(summary = "获取平台协议")
    public Result<String> getPlatformAgreement(String key){
        String value = "";
        if (StrUtil.equals(key,"user-agreement")){
            value = sysConfigService.getValueByKey(key);
        }else if (StrUtil.equals(key,"privacy-agreement")){
            value = sysConfigService.getValueByKey(key);
        }
        return Result.success("ok",value);
    }

}
