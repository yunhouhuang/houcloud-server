package com.houcloud.example.controller.front;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.houcloud.example.common.notice.dingtalk.DingTalkMessage;
import com.houcloud.example.common.result.Result;
import com.houcloud.example.common.security.token.handler.FrontTokenHandler;
import com.houcloud.example.common.security.token.model.FrontToken;
import com.houcloud.example.common.security.token.store.AuthUtil;
import com.houcloud.example.ext.wechat.model.AppletAuthorizeResponse;
import com.houcloud.example.ext.wechat.model.UserInfo;
import com.houcloud.example.ext.wechat.model.WechatAppletLoginBody;
import com.houcloud.example.ext.wechat.model.WechatMobileAuthBody;
import com.houcloud.example.ext.wechat.service.WechatAuthorizeService;
import com.houcloud.example.model.entity.User;
import com.houcloud.example.service.IUserLogService;
import com.houcloud.example.service.IUserNoticeService;
import com.houcloud.example.service.IUserService;
import com.houcloud.example.utils.IpUtil;
import com.houcloud.example.utils.MobileUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>
 * mark
 * </p>
 *
 * @author <a href="mailto:yunhouhuang@gmail.com">yunhouhuang@gmail.com</a>
 */
@Slf4j
@RestController
@Tag(name = "用户登录")
@RequestMapping("/api/front")
public class UserLoginController {

    @Resource
    private WechatAuthorizeService wechatAuthorizeService;

    @Resource
    private IUserService userService;

    @Resource
    private FrontTokenHandler frontTokenHandler;

    @Resource
    private IUserLogService userLogService;
    @Resource
    private IUserNoticeService userNoticeService;

    @Operation(summary = "密码登录")
    @PostMapping("/login")
    public Result<?> login() {
        return Result.fail("暂未开放");
    }

    @Operation(summary = "微信小程序授权登录")
    @PostMapping("/login/wechat/applet")
    @Transactional(rollbackFor = Exception.class)
    public Result<FrontToken> wechatAppletLogin(HttpServletRequest request, @Valid @RequestBody WechatAppletLoginBody body) {
        AppletAuthorizeResponse appletCodeVerifyResponse = wechatAuthorizeService.authorizeCode(body.getCode());
        if (StrUtil.isBlank(appletCodeVerifyResponse.getOpenid())){
            return Result.fail("微信授权失败");
        }
        User user = userService.getByWechatAppletOpenid(appletCodeVerifyResponse.getOpenid());
        if (Objects.isNull(user)) {
            // 自动注册
            user = new User();
            user.setWechatOpenid(appletCodeVerifyResponse.getOpenid());
            UserInfo userInfo = body.getUserInfo();
            if (Objects.nonNull(userInfo)) {
                user.setAvatar(userInfo.getAvatarUrl());
                user.setNickname(userInfo.getNickName());
            } else {
                // 默认头像
                user.setNickname("微信用户");
                user.setAvatar("https://thirdwx.qlogo.cn/mmopen/vi_32/POgEwh4mIHO4nibH0KlMECNjjGxQUq24ZEaGT4poC6icRiccVGKSyXwibcPq4BWmiaIGuG1icwxaQX6grC9VemZoJ8rg/132");
            }
            user.setUsername(IdUtil.fastSimpleUUID());
            boolean save = userService.save(user);
            if (!save) {
                return Result.fail("注册失败");
            }
            DingTalkMessage.sendMessage(StrUtil.format("新用户注册通知：来自{}的{}已注册。", IpUtil.getRealIp(request),user.getNickname()));
            userNoticeService.sendUserNotice(user.getId(), "欢迎加入HOUCLOUD！", "开启您的带货之旅吧~");
        }
        if (Objects.isNull(user.getId())) {
            return Result.fail("账号异常");
        }
        FrontToken frontToken = frontTokenHandler.createToken(user.getId(), 60 * 60 * 24 * 15L);
        userLogService.saveLoginLog("微信小程序授权登录", "登录成功", request);
        userService.update(Wrappers.<User>lambdaUpdate().eq(User::getId, user.getId()).set(User::getLastLiveAt, LocalDateTime.now()));
        return Result.success(frontToken);
    }

    @Operation(summary = "微信小程序授权手机号登录")
    @PostMapping("/login/wechat/applet/mobile")
    public Result<FrontToken> wechatAppletMobileLogin(HttpServletRequest request, @Valid @RequestBody WechatMobileAuthBody body) {
        String mobile = wechatAuthorizeService.checkBindingPhone(body);
        if (!MobileUtil.isMobile(mobile)) {
            return Result.fail("暂不支持此号码");
        }
        User user = userService.lambdaQuery().eq(User::getMobile, mobile).one();
        if (Objects.isNull(user)) {
            // 自动注册
            user = new User();
            user.setMobile(mobile);
            // 默认头像
            user.setNickname("微信用户");
            user.setAvatar("https://thirdwx.qlogo.cn/mmopen/vi_32/POgEwh4mIHO4nibH0KlMECNjjGxQUq24ZEaGT4poC6icRiccVGKSyXwibcPq4BWmiaIGuG1icwxaQX6grC9VemZoJ8rg/132");
            user.setUsername(IdUtil.fastSimpleUUID());
            boolean save = userService.save(user);
            if (!save) {
                return Result.fail("注册失败");
            }
            userNoticeService.sendUserNotice(user.getId(), "欢迎加入HOUCLOUD！", "开启您的带货之旅吧~");
        }
        if (Objects.isNull(user.getId())) {
            return Result.fail("账号异常");
        }
        FrontToken frontToken = frontTokenHandler.createToken(user.getId(), 60 * 60 * 24 * 15L);
        userLogService.saveLoginLog("微信小程序手机号授权登录", "登录成功", request);
        userService.update(Wrappers.<User>lambdaUpdate().eq(User::getId, user.getId()).set(User::getLastLiveAt, LocalDateTime.now()));
        return Result.success(frontToken);
    }

    @GetMapping("/login/state")
    public Result<Long> getLoginState() {
        Long userId = AuthUtil.tryGetUserId();
        return Result.success(userId);
    }


    @Operation(summary = "退出登录")
    @DeleteMapping("/logout")
    public Result<Void> logout() {
        Long userId = AuthUtil.tryGetUserId();
        if (Objects.nonNull(userId)) {
            frontTokenHandler.deleteTokenByDeveloperId(userId);
        }
        return Result.success();
    }

}
