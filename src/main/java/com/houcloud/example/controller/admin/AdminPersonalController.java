/*
 * Copyright (C) 2022 Yunhou·Huang  yunhouhuang@gmail.com.
 * All rights reserved.
 * Official Web Site: http://houcloud.com.
 */

package com.houcloud.example.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.houcloud.example.common.result.Result;
import com.houcloud.example.common.security.token.store.AuthContext;
import com.houcloud.example.model.entity.Admin;
import com.houcloud.example.model.entity.Role;
import com.houcloud.example.model.response.AdminPersonResponse;
import com.houcloud.example.model.response.UserPersonalResponse;
import com.houcloud.example.service.IAdminService;
import com.houcloud.example.service.IMenuService;
import com.houcloud.example.service.IRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 管理员个人接口
 * </p>
 *
 * @author <a href="mailto:yunhouhuang@gmail.com">yunhouhuang@gmail.com</a>
 */

@Tag(name = "管理员个人接口")
@RestController
@RequestMapping("/api/admin/personal")
public class AdminPersonalController {

    @Resource
    private IAdminService adminService;

    @Resource
    private IRoleService roleService;
    @Resource
    private IMenuService menuService;


    @Operation(summary = "获取个人资料")
    @GetMapping
    public Result<AdminPersonResponse> getPersonalInfo() {
        Long adminId = AuthContext.getAdminId();
        Admin admin = adminService.getById(adminId);
        if (Objects.isNull(admin)) {
            // 账号被删除注销等情况
            return Result.unauthorized();
        }
        AdminPersonResponse adminPersonResponse = BeanUtil.copyProperties(admin, AdminPersonResponse.class, "password");
        List<Role> roleListByAdminId = roleService.getRoleListByAdminId(adminId);
        adminPersonResponse.setRoles(roleListByAdminId);
        List<String> list = menuService.getAdminPermissions(adminId);
        adminPersonResponse.setPermissions(list);
        menuService.updatePermission(adminId);
        return Result.success(adminPersonResponse);
    }
    @Operation(summary = "修改个人资料")
    @PutMapping
    public Result<UserPersonalResponse> updatePersonalInfo(@RequestBody @Valid Admin admin) {
        Long adminId = AuthContext.getAdminId();
        LambdaUpdateChainWrapper<Admin> wrapper = adminService.lambdaUpdate().eq(Admin::getId, adminId);
        boolean changed = false;
        if (StrUtil.isNotBlank(admin.getAvatar())) {
            wrapper.set(Admin::getAvatar, admin.getAvatar());
            changed = true;
        }
        if (StrUtil.isNotBlank(admin.getNickname())) {
            wrapper.set(Admin::getNickname, admin.getNickname());
            changed = true;
        }
        if (StrUtil.isNotBlank(admin.getEmail())) {
            wrapper.set(Admin::getEmail, admin.getEmail());
            changed = true;
        }
        if (changed) {
            wrapper.update();
        }
        return Result.success();
    }

}
