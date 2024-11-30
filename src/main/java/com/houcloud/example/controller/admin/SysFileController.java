package com.houcloud.example.controller.admin;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.houcloud.example.common.logger.annotation.Logger;
import com.houcloud.example.common.result.Result;
import com.houcloud.example.common.security.permission.annotation.Permission;
import com.houcloud.example.model.entity.SysFile;
import com.houcloud.example.model.request.IdBody;
import com.houcloud.example.model.request.PageListParams;
import com.houcloud.example.service.ISysFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;


/**
 * <p>
 * 系统文件接口
 * </p>
 *
 * @author HouCloud
 * @since 2023-01-21
 */
@Tag(name = "系统文件接口")
@RestController
@RequestMapping("/api/admin/file")
public class SysFileController {

    @Resource
    private ISysFileService sysFileService;


    @Operation(summary = "获取系统文件详情")
    @GetMapping
    public Result<SysFile> getSysFile(@RequestParam Long id) {
        SysFile sysFile = sysFileService.getById(id);
        if (Objects.isNull(sysFile)) {
            return Result.notfound("系统文件未找到");
        }
        return Result.success(sysFile);
    }


    @Operation(summary = "获取系统文件列表")
    @GetMapping("/list")
    public Result<Page<SysFile>> getSysFileList(PageListParams params, Long cateId, String suffix, Long adminId) {
        LambdaQueryWrapper<SysFile> wrapper = Wrappers.<SysFile>lambdaQuery().orderByDesc(SysFile::getCreatedAt);
        if (StrUtil.isNotBlank(params.getKeywords())) {
            wrapper.like(SysFile::getOldName, params.getKeywords());
        }
        if (Objects.nonNull(cateId)) {
            wrapper.eq(SysFile::getCateId, cateId);
        }
        if (Objects.nonNull(adminId)) {
            wrapper.eq(SysFile::getAdminId, adminId);
        }
        if (StrUtil.isNotBlank(suffix)) {
            wrapper.eq(SysFile::getSuffix, suffix);
        }
        Page<SysFile> sysFilePage = sysFileService.page(Page.of(params.getPage(), params.getLimit()), wrapper);
        return Result.success(sysFilePage);
    }


    @Operation(summary = "上传文件")
    @PostMapping
    @Logger("上传文件")
    public Result<SysFile> upload(@RequestParam("file") MultipartFile file) {
        return Result.success("上传成功", sysFileService.uploadFile(file));
    }

    @Operation(summary = "更新系统文件")
    @PutMapping
    @Logger("更新文件")
    @Permission("sys:file:update")
    public Result<SysFile> updateSysFile(@RequestBody SysFile sysFile) {
        boolean save = sysFileService.updateById(sysFile);
        return save ? Result.success(sysFile) : Result.fail();
    }

    @Operation(summary = "删除系统文件")
    @DeleteMapping
    @Logger("删除文件")
    @Permission("sys:file:delete")
    public Result<SysFile> deleteSysFile(@Valid @RequestBody IdBody<Long> idBody) {
        SysFile sysFile = sysFileService.getById(idBody.getId());
        if (Objects.isNull(sysFile)) {
            return Result.fail("系统文件不存在");
        }
        boolean deleted = sysFileService.removeById(sysFile);
        return deleted ? Result.success(sysFile) : Result.fail();
    }
}
