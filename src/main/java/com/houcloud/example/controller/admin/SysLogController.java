package com.houcloud.example.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.houcloud.example.common.result.Result;
import com.houcloud.example.model.entity.Admin;
import com.houcloud.example.model.entity.SysLog;
import com.houcloud.example.model.request.DateScopeParams;
import com.houcloud.example.model.request.IdBody;
import com.houcloud.example.model.request.PageListParams;
import com.houcloud.example.model.response.SysLogResponse;
import com.houcloud.example.service.IAdminService;
import com.houcloud.example.service.ISysLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * <p>
 * 系统日志接口
 * </p>
 *
 * @author HouCloud
 * @since 2023-01-20
 */
@Tag(name = "系统日志接口")
@RestController
@RequestMapping("/api/admin/log")
public class SysLogController {

    @Resource
    private ISysLogService sysLogService;

    @Resource
    private IAdminService adminService;

    @Operation(summary = "获取系统日志详情")
    @GetMapping
    public Result<SysLog> getSysLog(@RequestParam Long id) {
        SysLog sysLog = sysLogService.getById(id);
        if (Objects.isNull(sysLog)) {
            return Result.noFound("系统日志未找到");
        }
        return Result.success(sysLog);
    }


    @Operation(summary = "获取系统日志列表")
    @GetMapping("/list")
    public Result<IPage<SysLogResponse>> getSysLogList(PageListParams params, String type,
                                                       DateScopeParams dateScope) {
        LambdaQueryWrapper<SysLog> wrapper = Wrappers.<SysLog>lambdaQuery().orderByDesc(SysLog::getCreatedAt);
        if (Objects.nonNull(dateScope)){
            dateScope.formatWrapper(wrapper,SysLog::getCreatedAt);
        }
        // 时间范围筛选
        if (Objects.nonNull(dateScope.getStartAt())) {
            if (Objects.isNull(dateScope.getEndAt())) {
                dateScope.setEndAt(LocalDateTime.now());
            }
            wrapper.between(SysLog::getCreatedAt, dateScope.getStartAt(), dateScope.getEndAt());
        }
        // 类型筛选
        if (StrUtil.isNotBlank(type)) {
            wrapper.eq(SysLog::getType, type);
        }
        // 关键词搜索
        if (StrUtil.isNotBlank(params.getKeywords())) {
            wrapper.like(SysLog::getTitle, params.getKeywords());
        }
        IPage<SysLog> sysLogPage = sysLogService.page(Page.of(params.getPage(), params.getLimit()), wrapper);
        if (CollUtil.isEmpty(sysLogPage.getRecords())) {
            return Result.success(sysLogPage.convert(r -> BeanUtil.toBean(r, SysLogResponse.class)));
        }
        List<Long> adminIds = sysLogPage.getRecords().stream().map(SysLog::getAdminId).toList();
        Map<Long, Admin> adminMap = adminService.getSimpleInfoMapByIds(adminIds);
        IPage<SysLogResponse> responseIPage = sysLogPage.convert(r -> {
            SysLogResponse sysLogResponse = BeanUtil.toBean(r, SysLogResponse.class);
            sysLogResponse.setAdmin(adminMap.get(r.getAdminId()));
            return sysLogResponse;
        });
        return Result.success(responseIPage);
    }


    @Operation(summary = "添加系统日志")
    @PostMapping
    public Result<SysLog> addSysLog(@RequestBody SysLog sysLog) {
        boolean save = sysLogService.save(sysLog);
        return save ? Result.success(sysLog) : Result.fail();
    }

    @Operation(summary = "更新系统日志")
    @PutMapping
    public Result<SysLog> updateSysLog(@RequestBody SysLog sysLog) {
        boolean save = sysLogService.updateById(sysLog);
        return save ? Result.success(sysLog) : Result.fail();
    }

    @Operation(summary = "删除系统日志")
    @DeleteMapping
    public Result<SysLog> deleteSysLog(@Valid @RequestBody IdBody<Long> idBody) {
        SysLog sysLog = sysLogService.getById(idBody.getId());
        if (Objects.isNull(sysLog)) {
            return Result.fail("系统日志不存在");
        }
        boolean deleted = sysLogService.removeById(sysLog);
        return deleted ? Result.success(sysLog) : Result.fail();
    }
}
