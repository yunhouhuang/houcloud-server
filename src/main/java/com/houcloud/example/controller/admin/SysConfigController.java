package com.houcloud.example.controller.admin;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.houcloud.example.common.logger.annotation.Logger;
import com.houcloud.example.common.result.Result;
import com.houcloud.example.common.security.permission.annotation.Permission;
import com.houcloud.example.model.entity.SysConfig;
import com.houcloud.example.model.request.IdBody;
import com.houcloud.example.model.request.PageListParams;
import com.houcloud.example.service.ISysConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


/**
 * <p>
 * 系统变量配置接口
 * </p>
 *
 * @author HouCloud
 * @since 2023-01-22
 */
@Tag(name = "系统变量配置接口")
@RestController
@RequestMapping("/api/admin/config")
public class SysConfigController {

    @Resource
    private ISysConfigService sysConfigService;

    @Operation(summary = "获取系统变量配置详情")
    @GetMapping
    public Result<SysConfig> getSysConfig(@RequestParam Long id) {
        SysConfig sysConfig = sysConfigService.getById(id);
        if (Objects.isNull(sysConfig)) {
            return Result.noFound("系统变量配置未找到");
        }
        return Result.success(sysConfig);
    }


    @Operation(summary = "获取系统变量配置列表")
    @GetMapping("/list")
    public Result<Page<SysConfig>> getSysConfigList(PageListParams params) {
        LambdaQueryWrapper<SysConfig> wrapper = Wrappers.<SysConfig>lambdaQuery().orderByDesc(SysConfig::getCreatedAt);
        Page<SysConfig> sysConfigPage = sysConfigService.page(Page.of(params.getPage(), params.getLimit()), wrapper);
        return Result.success(sysConfigPage);
    }


    @Operation(summary = "添加系统变量配置")
    @PostMapping
    @Logger("添加系统变量配置")
    @Permission("sys:config:add")
    public Result<SysConfig> addSysConfig(@RequestBody SysConfig sysConfig) {
        boolean save = sysConfigService.save(sysConfig);
        return save ? Result.success(sysConfig) : Result.fail();
    }

    @Operation(summary = "更新系统变量配置")
    @PutMapping
    @Permission("sys:config:update")
    @Logger("更新系统变量配置")
    public Result<SysConfig> updateSysConfig(@RequestBody SysConfig sysConfig) {
        boolean save = sysConfigService.updateById(sysConfig);
        return save ? Result.success(sysConfig) : Result.fail();
    }

    @Operation(summary = "删除系统变量配置")
    @DeleteMapping
    @Logger("删除系统变量配置")
    @Permission("sys:config:delete")
    public Result<SysConfig> deleteSysConfig(@Valid @RequestBody IdBody<Long> idBody) {
        SysConfig sysConfig = sysConfigService.getById(idBody.getId());
        if (Objects.isNull(sysConfig)) {
            return Result.fail("系统变量配置不存在");
        }
        boolean deleted = sysConfigService.removeById(sysConfig);
        return deleted ? Result.success(sysConfig) : Result.fail();
    }
}
