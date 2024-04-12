package com.houcloud.example.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.houcloud.example.common.logger.annotation.Logger;
import com.houcloud.example.common.result.Result;
import com.houcloud.example.common.security.permission.annotation.Permission;
import com.houcloud.example.model.entity.User;
import com.houcloud.example.model.request.PageListParams;
import com.houcloud.example.model.request.SetBoolBody;
import com.houcloud.example.model.response.UserResponse;
import com.houcloud.example.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author HouCloud
 * @since 2023-01-16
 */
@Slf4j
@RestController
@Tag(name = "用户")
@RequestMapping("/api/admin/user")
public class UserManageController {

    @Resource
    private IUserService userService;


    @Operation(summary = "获取用户")
    @GetMapping
    public Result<UserResponse> getUser(@RequestParam Long id) {
        User user = userService.getById(id);
        if (Objects.isNull(user)) {
            return Result.noFound("用户未找到");
        }
        UserResponse response = BeanUtil.toBean(user, UserResponse.class);
        return Result.success(response);
    }

    @Operation(summary = "获取用户列表")
    @GetMapping("/list")
    public Result<IPage<UserResponse>> getUserList(PageListParams params) {
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery().orderByDesc(User::getCreatedAt);
        if (StrUtil.isNotBlank(params.getKeywords())) {
            wrapper.like(User::getNickname, params.getKeywords());
        }
        IPage<UserResponse> userPage = userService.page(Page.of(params.getPage(), params.getLimit()), wrapper).convert(r -> BeanUtil.toBean(r, UserResponse.class));
        if (CollUtil.isEmpty(userPage.getRecords())) {
            return Result.success(userPage);
        }
        return Result.success(userPage);
    }


    @Operation(summary = "拉黑或解除拉黑")
    @PutMapping("/blacklist")
    @Permission("user:blacklist")
    @Logger("拉黑或解除拉黑")
    public Result<User> setBlacklist(@RequestBody SetBoolBody<Long> body) {
        User user = userService.getById(body.getId());
        if (Objects.isNull(user)) {
            return Result.noFound("用户未找到");
        }
        boolean update = userService.lambdaUpdate().eq(User::getId, body.getId()).set(User::getBlacklist, body.getValue()).update();
        return update ? Result.success(user) : Result.fail();
    }

    @Operation(summary = "锁定或解除锁定")
    @PutMapping("/lock")
    @Permission("user:lock")
    @Logger("锁定或解除锁定")
    public Result<User> setUserLock(@RequestBody SetBoolBody<Long> body) {
        User user = userService.getById(body.getId());
        if (Objects.isNull(user)) {
            return Result.noFound("用户未找到");
        }
        boolean update = userService.lambdaUpdate().eq(User::getId, body.getId()).set(User::getLocked, body.getValue()).update();
        return update ? Result.success(user) : Result.fail();
    }
}
