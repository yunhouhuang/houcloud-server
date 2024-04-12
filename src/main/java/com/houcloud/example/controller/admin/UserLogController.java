package com.houcloud.example.controller.admin;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.houcloud.example.common.result.Result;
import com.houcloud.example.model.entity.UserLog;
import com.houcloud.example.model.request.IdBody;
import com.houcloud.example.model.request.PageListParams;
import com.houcloud.example.service.IUserLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


/**
 * <p>
 * 用户日志接口
 * </p>
 *
 * @author HouCloud
 * @since 2023-01-24
 */
@Tag(name = "用户日志接口")
@RestController
@RequestMapping("/api/admin/user/log")
public class UserLogController {

    @Resource
    private IUserLogService userLogService;

    @Operation(summary = "获取用户日志详情")
    @GetMapping
    public Result<UserLog> getUserLog(@RequestParam Long id) {
        UserLog userLog = userLogService.getById(id);
        if (Objects.isNull(userLog)) {
            return Result.noFound("用户日志未找到");
        }
        return Result.success(userLog);
    }


    @Operation(summary = "获取用户日志列表")
    @GetMapping("/list")
    public Result<Page<UserLog>> getUserLogList(PageListParams params, Long userId) {
        LambdaQueryWrapper<UserLog> wrapper = Wrappers.<UserLog>lambdaQuery().orderByDesc(UserLog::getCreatedAt);
        if (StrUtil.isNotBlank(params.getKeywords())) {
            wrapper.like(UserLog::getTitle, params.getKeywords());
        }
        if (Objects.nonNull(userId)) {
            wrapper.eq(UserLog::getUserId, userId);
        }
        Page<UserLog> userLogPage = userLogService.page(Page.of(params.getPage(), params.getLimit()), wrapper);
        return Result.success(userLogPage);
    }


    @Operation(summary = "删除用户日志")
    @DeleteMapping
    public Result<UserLog> deleteUserLog(@Valid @RequestBody IdBody<Long> idBody) {
        UserLog userLog = userLogService.getById(idBody.getId());
        if (Objects.isNull(userLog)) {
            return Result.fail("用户日志不存在");
        }
        boolean deleted = userLogService.removeById(userLog);
        return deleted ? Result.success(userLog) : Result.fail();
    }
}
