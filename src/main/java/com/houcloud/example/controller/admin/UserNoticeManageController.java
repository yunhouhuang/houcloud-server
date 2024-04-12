package com.houcloud.example.controller.admin;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.houcloud.example.common.result.Result;
import com.houcloud.example.model.entity.UserNotice;
import com.houcloud.example.model.request.DateScopeParams;
import com.houcloud.example.model.request.IdBody;
import com.houcloud.example.model.request.PageListParams;
import com.houcloud.example.service.IUserNoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


/**
 * <p>
 * 用户消息通知接口
 * </p>
 *
 * @author HouCloud
 * @since 2023-01-24
 */
@Tag(name = "用户消息通知接口")
@RestController
@RequestMapping("/api/admin/user/notice")
public class UserNoticeManageController {

    @Resource
    private IUserNoticeService userNoticeService;

    @Operation(summary = "获取用户消息通知详情")
    @GetMapping
    public Result<UserNotice> getUserNotice(@RequestParam Long id) {
        UserNotice userNotice = userNoticeService.getById(id);
        if (Objects.isNull(userNotice)) {
            return Result.noFound("用户消息通知未找到");
        }
        return Result.success(userNotice);
    }


    @Operation(summary = "获取用户消息通知列表")
    @GetMapping("/list")
    public Result<Page<UserNotice>> getUserNoticeList(PageListParams params, Long userId,DateScopeParams dateScopeParams) {
        LambdaQueryWrapper<UserNotice> wrapper = Wrappers.<UserNotice>lambdaQuery().orderByDesc(UserNotice::getCreatedAt);
        if (StrUtil.isNotBlank(params.getKeywords())) {
            wrapper.like(UserNotice::getTitle, params.getKeywords());
        }
        dateScopeParams.formatWrapper(wrapper,UserNotice::getCreatedAt);
        if (Objects.nonNull(userId)) {
            wrapper.eq(UserNotice::getUserId, userId);
        }
        Page<UserNotice> userNoticePage = userNoticeService.page(Page.of(params.getPage(), params.getLimit()), wrapper);
        return Result.success(userNoticePage);
    }

    @Operation(summary = "添加用户消息通知")
    @PostMapping
    public Result<UserNotice> addUserNotice(@RequestBody UserNotice userNotice) {
        boolean save = userNoticeService.save(userNotice);
        return save ? Result.success(userNotice) : Result.fail();
    }

    @Operation(summary = "更新用户消息通知")
    @PutMapping
    public Result<UserNotice> updateUserNotice(@RequestBody UserNotice userNotice) {
        boolean save = userNoticeService.updateById(userNotice);
        return save ? Result.success(userNotice) : Result.fail();
    }

    @Operation(summary = "删除用户消息通知")
    @DeleteMapping
    public Result<UserNotice> deleteUserNotice(@Valid @RequestBody IdBody<Long> idBody) {
        UserNotice userNotice = userNoticeService.getById(idBody.getId());
        if (Objects.isNull(userNotice)) {
            return Result.fail("用户消息通知不存在");
        }
        boolean deleted = userNoticeService.removeById(userNotice);
        return deleted ? Result.success(userNotice) : Result.fail();
    }
}
