package com.houcloud.example.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.houcloud.example.common.constants.RegexpEnums;
import com.houcloud.example.common.logger.annotation.Logger;
import com.houcloud.example.common.result.Result;
import com.houcloud.example.common.security.permission.annotation.Permission;
import com.houcloud.example.common.security.token.handler.AdminTokenHandler;
import com.houcloud.example.common.security.token.store.AuthUtil;
import com.houcloud.example.model.entity.Admin;
import com.houcloud.example.model.entity.AdminRoleRef;
import com.houcloud.example.model.entity.Role;
import com.houcloud.example.model.request.*;
import com.houcloud.example.model.response.AdminResponse;
import com.houcloud.example.service.IAdminRoleRefService;
import com.houcloud.example.service.IAdminService;
import com.houcloud.example.service.IRoleService;
import com.houcloud.example.utils.MobileUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * <p>
 * 管理员接口
 * </p>
 *
 * @author HouCloud
 * @since 2023-01-18
 */
@Slf4j
@Tag(name = "管理员接口")
@RestController
@RequestMapping("/api/admin/admin")
public class AdminManageController {

    @Resource
    private IAdminService adminService;

    @Resource
    private AdminTokenHandler adminTokenHandler;

    @Resource
    private IRoleService roleService;
    @Resource
    private IAdminRoleRefService adminRoleRefService;

    @Permission("admin:detail")
    @Operation(summary = "获取管理员详情")
    @GetMapping
    public Result<AdminResponse> getAdmin(@RequestParam Long id) {
        Admin admin = adminService.getById(id);
        if (Objects.isNull(admin)) {
            return Result.noFound("管理员未找到");
        }
        AdminResponse adminResponse = BeanUtil.toBean(admin, AdminResponse.class);
        List<Role> roleList = roleService.getRoleListByAdminId(id);
        adminResponse.setRoles(roleList);
        return Result.success(adminResponse);
    }


    @Permission("admin:list")
    @Operation(summary = "获取管理员列表")
    @GetMapping("/list")
    public Result<IPage<AdminResponse>> getAdminList(PageListParams params) {
        LambdaQueryWrapper<Admin> wrapper = Wrappers.<Admin>lambdaQuery().orderByDesc(Admin::getCreatedAt);
        if (StrUtil.isNotBlank(params.getKeywords())) {
            wrapper.like(Admin::getMobile, params.getKeywords()).or(r -> r.like(Admin::getNickname, params.getKeywords()));
        }
        IPage<AdminResponse> adminPage = adminService.page(Page.of(params.getPage(), params.getLimit()), wrapper).convert(r -> BeanUtil.toBean(r, AdminResponse.class));
        if (CollUtil.isEmpty(adminPage.getRecords())) {
            return Result.success(adminPage);
        }
        List<Long> adminIds = adminPage.getRecords().stream().map(Admin::getId).toList();
        Map<Long, List<Role>> rolesMap = roleService.getRolesMapByAdminIds(adminIds);
        for (AdminResponse record : adminPage.getRecords()) {
            if (CollUtil.isNotEmpty(rolesMap)) {
                List<Role> roles = rolesMap.get(record.getId());
                record.setRoles(roles);
            }
        }
        return Result.success(adminPage);
    }


    @Permission("admin:add")
    @Operation(summary = "添加管理员")
    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    public Result<Admin> addAdmin(@Valid @RequestBody AddAdminBody body) {
        Long adminId = AuthUtil.getAdminId();
        Admin myInfo = adminService.getById(adminId);
        if (!myInfo.getIsCreator()) {
            return Result.fail("无权操作");
        }
        if (body.getNickname().length() > 32) {
            return Result.fail("姓名太长");
        }
        Admin admin = new Admin();
        admin.setAvatar(body.getAvatar());
        admin.setNickname(body.getNickname());
        if (!checkUsername(body.getUsername())) {
            return Result.fail("用户名字母开头长度4~20位");
        }
        admin.setUsername(body.getUsername());
        if (!checkPwd(body.getPassword())) {
            return Result.fail("请规范密码长度6~20位之间");
        }
        String hashpw = BCrypt.hashpw(body.getPassword());
        admin.setPassword(hashpw);
        if (StrUtil.isNotBlank(body.getMobile())) {
            if (MobileUtil.isMobile(body.getMobile())) {
                admin.setMobile(body.getMobile());
            } else {
                return Result.fail("手机号码格式错误");
            }
        }
        if (StrUtil.isNotBlank(body.getEmail())) {
            if (body.getEmail().matches(RegexpEnums.EMAIL_REGULAR.getContent())) {
                admin.setMobile(body.getMobile());
            } else {
                return Result.fail("邮箱格式错误");
            }
        }
        boolean save = adminService.save(admin);
        if (!save) {
            return Result.fail("创建失败");
        }
        adminRoleRefService.lambdaUpdate().eq(AdminRoleRef::getAdminId, admin.getId()).remove();
        List<AdminRoleRef> refs = CollUtil.newArrayList();
        for (Long roleId : body.getRoleIds()) {
            AdminRoleRef adminRoleRef = new AdminRoleRef();
            adminRoleRef.setAdminId(admin.getId());
            adminRoleRef.setRoleId(roleId);
            refs.add(adminRoleRef);
        }
        boolean batch = adminRoleRefService.saveBatch(refs);
        return batch ? Result.success(admin) : Result.fail();
    }

    private boolean checkUsername(String username) {
        String regExp = "^[^0-9][\\w_]{3,20}$";
        return username.matches(regExp);
    }

    private boolean checkPwd(String pwd) {
        String regExp = "^[\\w_]{5,20}$";
        return pwd.matches(regExp);
    }

    @Permission("admin:update")
    @Operation(summary = "更新管理员")
    @PutMapping
    public Result<Void> updateAdmin(@Valid @RequestBody UpdateAdminBody body) {
        Long adminId = AuthUtil.getAdminId();
        Admin myInfo = adminService.getById(adminId);
        if (!myInfo.getIsCreator()) {
            return Result.fail("无权操作");
        }
        Admin byId = adminService.getById(body.getId());
        if (Objects.isNull(byId)) {
            return Result.noFound();
        }
        LambdaUpdateChainWrapper<Admin> wrapper = adminService.lambdaUpdate().eq(Admin::getId, body.getId());
        if (body.getNickname().length() > 32) {
            return Result.fail("姓名太长");
        }
        if (StrUtil.isNotBlank(body.getNickname())) {
            if (!checkUsername(body.getUsername())) {
                return Result.fail("用户名字母开头长度4~20位");
            }
            wrapper.set(Admin::getNickname, body.getNickname());
        }
        if (StrUtil.isNotBlank(body.getAvatar())) {
            wrapper.set(Admin::getAvatar, body.getAvatar());
        }
        if (StrUtil.isNotBlank(body.getPassword())) {
            if (!checkPwd(body.getPassword())) {
                return Result.fail("请规范密码长度6~20位之间");
            }
            if (!AuthUtil.hasPermission("admin:update:password") && !adminId.equals(body.getId())) {
                return Result.fail("您无权修改管理员密码");
            }
            log.info("管理员{}修改了{}的密码", adminId, body.getId());
            String hashpw = BCrypt.hashpw(body.getPassword());
            wrapper.set(Admin::getPassword, hashpw);
        }
        wrapper.set(Admin::getMobile, body.getMobile());
        wrapper.set(Admin::getEmail, body.getEmail());
        if (!wrapper.update()) {
            return Result.fail();
        }
        adminRoleRefService.lambdaUpdate().eq(AdminRoleRef::getAdminId, body.getId()).remove();
        List<AdminRoleRef> refs = CollUtil.newArrayList();
        for (Long roleId : body.getRoleIds()) {
            AdminRoleRef adminRoleRef = new AdminRoleRef();
            adminRoleRef.setAdminId(body.getId());
            adminRoleRef.setRoleId(roleId);
            refs.add(adminRoleRef);
        }
        boolean batch = adminRoleRefService.saveBatch(refs);
        return batch ? Result.success() : Result.fail();
    }

    @Permission("admin:delete")
    @Operation(summary = "删除管理员")
    @DeleteMapping
    public Result<Admin> deleteAdmin(@Valid @RequestBody IdBody<Long> idBody) {
        Long adminId = AuthUtil.getAdminId();
        Admin myInfo = adminService.getById(adminId);
        if (!myInfo.getIsCreator()) {
            return Result.fail("无权操作");
        }
        if (adminId.equals(idBody.getId())) {
            return Result.fail("不能删除自己");
        }
        Admin admin = adminService.getById(idBody.getId());
        if (Objects.isNull(admin)) {
            return Result.fail("管理员不存在");
        }
        if (admin.getIsCreator()) {
            return Result.fail("创始人账号不可删除");
        }
        boolean deleted = adminService.removeById(admin);
        return deleted ? Result.success(admin) : Result.fail();
    }

    @Operation(summary = "锁定或解除锁定")
    @PutMapping("/lock")
    @Permission("admin:lock")
    @Logger("锁定或解除锁定")
    public Result<Admin> setUserLock(@RequestBody SetBoolBody<Long> body) {
        Long adminId = AuthUtil.getAdminId();
        Admin me = adminService.getById(adminId);
        if (!me.getIsCreator()) {
            return Result.fail("只有创始人可操作");
        }
        Admin admin = adminService.getById(body.getId());
        if (Objects.isNull(admin)) {
            return Result.noFound("管理员未找到");
        }
        if (admin.getIsCreator()) {
            return Result.fail("创始人账号不可锁定");
        }
        boolean update = adminService.lambdaUpdate().eq(Admin::getId, body.getId()).set(Admin::getLocked, body.getValue()).update();
        if (update){
            adminTokenHandler.deleteTokenByAdminId(adminId);
            return Result.success();
        }
        return Result.fail();
    }

}
