package com.houcloud.example.controller.admin;

import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.houcloud.example.common.result.Result;
import com.houcloud.example.common.security.token.handler.AdminTokenHandler;
import com.houcloud.example.common.security.token.model.AdminToken;
import com.houcloud.example.common.security.token.store.AuthContext;
import com.houcloud.example.model.entity.Admin;
import com.houcloud.example.model.request.PasswordLoginBody;
import com.houcloud.example.service.IAdminService;
import com.houcloud.example.service.IMenuService;
import com.houcloud.example.service.ISysLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * mark
 * </p>
 *
 * @author hou
 * @ 2023/1/10
 */
@Tag(name = "登录")
@Slf4j
@RestController
@RequestMapping("/api/admin")
public class AdminLoginController {

    @Resource
    private AdminTokenHandler adminTokenHandler;

    @Resource
    private IAdminService adminService;

    @Resource
    private IMenuService menuService;

    @Resource
    private ISysLogService sysLogService;

    @Operation(summary = "密码登录")
    @PostMapping("/login")
    public Result<AdminToken> login(HttpServletRequest request, @Valid @RequestBody PasswordLoginBody body) {
        LambdaQueryWrapper<Admin> wrapper = Wrappers.<Admin>query().lambda();
        wrapper.eq(Admin::getUsername, body.getAccount());
        Admin admin = adminService.getOne(wrapper);
        if (Objects.isNull(admin)) {
            return Result.fail("账号不存在");
        }
        if (admin.getLocked()) {
            return Result.fail("你的账号已被锁定");
        }
        String password = body.getPassword();
        boolean pwdChecked = BCrypt.checkpw(password, admin.getPassword());
        if (!pwdChecked) {
            return Result.fail("密码错误");
        }
        // 记录登录日志
        sysLogService.saveLoginLog(request, admin.getId(), admin.getUsername(), true);
        adminService.update(Wrappers.<Admin>lambdaUpdate().set(Admin::getLastLiveAt, LocalDateTime.now()).eq(Admin::getId, admin.getId()));
        List<String> permissions = menuService.getAdminPermissions(admin.getId());
        log.info("{}", permissions);
        // token 有效 1小时
        AdminToken token = adminTokenHandler.createToken(admin.getId(), (long) 60 * 60, permissions, false);
        return Result.success(token);
    }

    public static void main(String[] args) {
        System.out.println(BCrypt.hashpw("houcloud"));
    }

    @Operation(summary = "退出登录")
    @DeleteMapping("/logout")
    public Result<AdminToken> logout() {
        Long adminId = AuthContext.tryGetAdminId();
        if (Objects.nonNull(adminId)) {
            adminTokenHandler.deleteTokenByAdminId(adminId);
        }
        return Result.success();
    }
}
