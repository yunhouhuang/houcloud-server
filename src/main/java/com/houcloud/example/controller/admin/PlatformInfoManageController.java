package com.houcloud.example.controller.admin;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.houcloud.example.common.logger.annotation.Logger;
import com.houcloud.example.common.result.Result;
import com.houcloud.example.common.security.permission.annotation.Permission;
import com.houcloud.example.model.entity.SysConfig;
import com.houcloud.example.model.platform.PlatformInfo;
import com.houcloud.example.model.response.SysConfigBody;
import com.houcloud.example.service.ISysConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Tag(name = "平台信息管理")
@RequestMapping("/api/admin/platform")
public class PlatformInfoManageController {

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

    @PutMapping
    @Operation(summary = "设置平台信息")
    @Logger("设置平台信息")
    @Permission("platform:update")
    public Result<PlatformInfo> setPlatformInfo(@RequestBody PlatformInfo platformInfo){
        long count = sysConfigService.count(Wrappers.<SysConfig>lambdaQuery().eq(SysConfig::getKey,"platform-info"));
        String json = JSONUtil.toJsonStr(platformInfo);
        if (count==0){
            SysConfig sysConfig = new SysConfig();
            sysConfig.setName("平台信息");
            sysConfig.setValue(json);
            sysConfig.setKey("platform-info");
            return sysConfigService.save(sysConfig)?Result.success(platformInfo):Result.fail();
        }
        sysConfigService.setValueByKey("platform-info",json);
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

    @PutMapping("/agreement")
    @Operation(summary = "设置平台协议")
    @Logger("设置平台协议")
    @Permission("platform:agreement:update")
    public Result<Void> setPlatformAgreement(@RequestBody SysConfigBody body){
        boolean updated = false;
        if (StrUtil.equals(body.getKey(),"user-agreement")){
            updated = sysConfigService.lambdaUpdate().set(SysConfig::getValue,body.getValue()).eq(SysConfig::getKey,body.getKey()).update();
        }else if (StrUtil.equals(body.getKey(),"privacy-agreement")){
            updated = sysConfigService.lambdaUpdate().set(SysConfig::getValue,body.getValue()).eq(SysConfig::getKey,body.getKey()).update();
        }
        return updated ? Result.success():Result.fail();
    }
}