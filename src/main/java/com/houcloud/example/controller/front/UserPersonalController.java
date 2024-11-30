/*
 * Copyright (C) 2022 Yunhou·Huang  yunhouhuang@gmail.com.
 * All rights reserved.
 * Official Web Site: http://houcloud.com.
 */

package com.houcloud.example.controller.front;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.houcloud.example.common.constants.Constants;
import com.houcloud.example.common.result.Result;
import com.houcloud.example.common.security.interceptor.authorize.Authorize;
import com.houcloud.example.common.security.token.store.AuthContext;
import com.houcloud.example.ext.wechat.model.WechatMobileAuthBody;
import com.houcloud.example.ext.wechat.service.WechatAuthorizeService;
import com.houcloud.example.model.entity.User;
import com.houcloud.example.model.entity.UserNotice;
import com.houcloud.example.model.response.UserPersonalResponse;
import com.houcloud.example.service.*;
import com.houcloud.example.utils.MobileUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * <p>
 * 用户个人中心
 * </p>
 *
 * @author <a href="mailto:yunhouhuang@gmail.com">yunhouhuang@gmail.com</a>
 */
@Slf4j
@RestController
@Tag(name = "用户个人中心")
@RequestMapping("/api/front/personal")
@Authorize
public class UserPersonalController {

    @Resource
    private IUserService userService;

    @Resource
    private IUserNoticeService userNoticeService;


    @Resource
    private WechatAuthorizeService wechatAuthorizeService;


    @Operation(summary = "获取个人资料")
    @GetMapping
    public Result<UserPersonalResponse> getPersonalInfo() {
        Long userId = AuthContext.getUserId();
        User user = userService.getById(userId);
        if (Objects.isNull(user)) {
            return Result.unauthorized();
        }
        UserPersonalResponse response = BeanUtil.toBean(user, UserPersonalResponse.class);
        response.setMobile(MobileUtil.decodeMobile(response.getMobile()));
        response.setNoticeMsgNum(userNoticeService.lambdaQuery().eq(UserNotice::getUserId, userId).eq(UserNotice::getStatus, Constants.UNREAD_STATUS).count());
        return Result.success(response);
    }

    @Operation(summary = "修改个人资料")
    @PutMapping
    public Result<UserPersonalResponse> updatePersonalInfo(@RequestBody @Valid User user) {
        Long userId = AuthContext.getUserId();
        LambdaUpdateChainWrapper<User> wrapper = userService.lambdaUpdate().eq(User::getId, userId);
        int setCount = 0;
        if (StrUtil.isNotBlank(user.getAvatar())) {
            wrapper.set(User::getAvatar, user.getAvatar());
            setCount++;
        }
        if (StrUtil.isNotBlank(user.getNickname())) {
            wrapper.set(User::getNickname, user.getNickname());
            setCount++;
        }
        if (setCount > 0) {
            wrapper.update();
        }
        return Result.success();
    }

    @Operation(summary = "绑定微信手机号")
    @PostMapping("/mobile")
    public Result<UserPersonalResponse> updateMobile(@RequestBody @Valid WechatMobileAuthBody body) {
        Long userId = AuthContext.getUserId();
        String mobile = wechatAuthorizeService.checkBindingPhone(body);
        if (!MobileUtil.isMobile(mobile)) {
            return Result.fail("暂不支持此号码");
        }
        User user = userService.lambdaQuery().eq(User::getMobile, mobile).one();
        if (Objects.nonNull(user) && !user.getId().equals(userId)) {
            return Result.fail("此号码已被绑定");
        }
        userService.lambdaUpdate().eq(User::getId, userId)
                .set(User::getMobile, mobile)
                .update();
        return Result.success();
    }

    @Operation(summary = "设置实名信息")
    @PutMapping("/certificate")
    public Result<UserPersonalResponse> updateCertificate(@RequestBody @Valid User user) {
        Long userId = AuthContext.getUserId();
        userService.lambdaUpdate()
                .eq(User::getId, userId)
                .set(User::getRealName, user.getRealName())
                .set(User::getIdCardNo, user.getIdCardNo())
                .update();
        return Result.success();
    }

}
