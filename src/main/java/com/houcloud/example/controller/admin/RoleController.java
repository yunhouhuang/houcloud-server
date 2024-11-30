package com.houcloud.example.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.houcloud.example.common.logger.annotation.Logger;
import com.houcloud.example.common.result.Result;
import com.houcloud.example.common.security.permission.annotation.Permission;
import com.houcloud.example.model.entity.Menu;
import com.houcloud.example.model.entity.Role;
import com.houcloud.example.model.entity.RoleMenuRef;
import com.houcloud.example.model.request.AddRoleBody;
import com.houcloud.example.model.request.IdBody;
import com.houcloud.example.model.request.PageListParams;
import com.houcloud.example.model.request.UpdateRoleBody;
import com.houcloud.example.model.response.RoleResponse;
import com.houcloud.example.service.IMenuService;
import com.houcloud.example.service.IRoleMenuRefService;
import com.houcloud.example.service.IRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


/**
 * <p>
 * 角色接口
 * </p>
 *
 * @author HouCloud
 * @since 2023-01-17
 */
@Tag(name = "角色接口")
@RestController
@RequestMapping("/api/admin/role")
public class RoleController {

    @Resource
    private IRoleService roleService;

    @Resource
    private IMenuService menuService;
    @Resource
    private IRoleMenuRefService roleMenuRefService;

    @Operation(summary = "获取角色详情")
    @GetMapping
    public Result<RoleResponse> getRole(@RequestParam Long id) {
        Role role = roleService.getById(id);
        if (Objects.isNull(role)) {
            return Result.notfound("角色未找到");
        }
        RoleResponse roleResponse = BeanUtil.toBean(role, RoleResponse.class);
        List<RoleMenuRef> roleMenuRefs = roleMenuRefService.list(Wrappers.<RoleMenuRef>lambdaQuery().eq(RoleMenuRef::getRoleId, role.getId()));
        if (CollUtil.isEmpty(roleMenuRefs)) {
            return Result.success(roleResponse);
        }
        roleResponse.setMenuIds(roleMenuRefs.stream().map(RoleMenuRef::getMenuId).toList());
        return Result.success(roleResponse);
    }


    @Operation(summary = "获取角色列表")
    @GetMapping("/list")
    @Permission("role:list")
    public Result<Page<Role>> getRoleList(PageListParams params) {
        LambdaQueryWrapper<Role> wrapper = Wrappers.<Role>lambdaQuery().orderByDesc(Role::getCreatedAt);
        if (StrUtil.isNotBlank(params.getKeywords())) {
            wrapper.like(Role::getName,params.getKeywords());
        }
        Page<Role> rolePage = roleService.page(Page.of(params.getPage(), params.getLimit()), wrapper);
        return Result.success(rolePage);
    }


    @Operation(summary = "添加角色")
    @PostMapping
    @Logger("添加角色")
    @Permission("role:add")
    @Transactional(rollbackFor = Exception.class)
    public Result<Role> addRole(@RequestBody AddRoleBody body) {
        Role role = new Role();
        role.setCode(body.getCode());
        role.setName(body.getName());
        boolean save = roleService.save(role);
        if (CollUtil.isEmpty(body.getMenuIds())) {
            return save ? Result.success(role) : Result.fail();
        }
        // 处理分配菜单
        boolean saveRefs = saveRefs(body.getMenuIds(), role.getId());
        return save && saveRefs ? Result.success(role) : Result.fail();
    }

    @Operation(summary = "更新角色")
    @PutMapping
    @Logger("更新角色")
    @Permission("role:update")
    @Transactional(rollbackFor = Exception.class)
    public Result<Role> updateRole(@RequestBody UpdateRoleBody body) {
        Role role = new Role();
        role.setId(body.getId());
        role.setCode(body.getCode());
        role.setName(body.getName());
        // 处理分配菜单
        boolean save = roleService.updateById(role);
        // 清空权限
        roleMenuRefService.remove(Wrappers.<RoleMenuRef>lambdaQuery().eq(RoleMenuRef::getRoleId, role.getId()));
        if (CollUtil.isEmpty(body.getMenuIds())) {
            return save ? Result.success(role) : Result.fail();
        }
        // 重新分配
        boolean saveRefs = saveRefs(body.getMenuIds(), role.getId());
        return save && saveRefs ? Result.success(role) : Result.fail();
    }

    private boolean saveRefs(List<Long> menuIds, Long roleId) {
        if (CollUtil.isEmpty(menuIds)) {
            return true;
        }
        // 处理分配菜单
        List<Menu> menus = menuService.list(Wrappers.<Menu>lambdaQuery().select(Menu::getId).in(Menu::getId, menuIds));
        if (CollUtil.isEmpty(menus)) {
            return true;
        }
        List<RoleMenuRef> refList = Lists.newArrayList();
        for (Menu menu : menus) {
            RoleMenuRef roleMenuRef = new RoleMenuRef();
            roleMenuRef.setRoleId(roleId);
            roleMenuRef.setMenuId(menu.getId());
            refList.add(roleMenuRef);
        }
        return roleMenuRefService.saveBatch(refList);
    }

    @Operation(summary = "删除角色")
    @DeleteMapping
    public Result<Role> deleteRole(@Valid @RequestBody IdBody<Long> idBody) {
        Role role = roleService.getById(idBody.getId());
        if (Objects.isNull(role)) {
            return Result.fail("角色不存在");
        }
        boolean deleted = roleService.removeById(role);
        return deleted ? Result.success(role) : Result.fail();
    }
}
