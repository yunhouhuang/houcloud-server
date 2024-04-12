package com.houcloud.example.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.houcloud.example.common.constants.Constants;
import com.houcloud.example.common.exception.BusinessException;
import com.houcloud.example.common.security.token.handler.AdminTokenHandler;
import com.houcloud.example.common.security.token.store.AuthUtil;
import com.houcloud.example.mapper.MenuMapper;
import com.houcloud.example.model.entity.AdminRoleRef;
import com.houcloud.example.model.entity.Menu;
import com.houcloud.example.model.entity.Role;
import com.houcloud.example.model.entity.RoleMenuRef;
import com.houcloud.example.model.dto.MenuTree;
import com.houcloud.example.model.dto.TreeUtil;
import com.houcloud.example.service.IAdminRoleRefService;
import com.houcloud.example.service.IMenuService;
import com.houcloud.example.service.IRoleMenuRefService;
import com.houcloud.example.service.IRoleService;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.houcloud.example.common.constants.Constants.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author HouCloud
 * @since 2023-01-17
 */
@Slf4j
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {


    private final IRoleService roleService;
    private final IAdminRoleRefService adminRoleRefService;
    private final IRoleMenuRefService roleMenuRefService;


    @Resource
    private AdminTokenHandler adminTokenHandler;
    @Async
    @Override
    public void updatePermission(Long adminId){
        List<String> adminPermissions = getAdminPermissions(adminId);
        adminTokenHandler.updatePermission(adminId,adminPermissions);
    }

    @Override
    public List<MenuTree> getMenuTree(Boolean lazy,Boolean all, Long parentId) {
        Long adminId = AuthUtil.getContextUser().getAdminId();
        List<AdminRoleRef> adminRoleRefs = adminRoleRefService.list(Wrappers.<AdminRoleRef>lambdaQuery().eq(AdminRoleRef::getAdminId, adminId));
        if (CollUtil.isEmpty(adminRoleRefs)) {
            return Collections.emptyList();
        }
        Set<Long> menuIds = getRoleMenuByRoleIds( adminRoleRefs.stream().map(AdminRoleRef::getRoleId).toList());
        if (CollUtil.isEmpty(menuIds)){
            return Collections.emptyList();
        }
        LambdaQueryWrapper<Menu> wrapper = Wrappers.<Menu>lambdaQuery().in(Menu::getId, menuIds)
                .orderByDesc(Menu::getWeight);
        if (Objects.isNull(all)||!all) {
            wrapper.eq(Menu::getType,"0");
        }
        if (Objects.isNull(lazy)||!lazy) {
            return TreeUtil.buildTree(list(wrapper), Constants.ROOT_MENU_ID);
        }

        long parent = parentId == null ? Constants.ROOT_MENU_ID : parentId;
        wrapper.eq(Menu::getParentId, parent);
        return TreeUtil.buildTree(
                list(wrapper),
                parent);
    }

    @Override
    public List<String> getAdminPermissions(Long adminId) {
        List<String> permissions = Lists.newArrayList();
        List<Role> roleList = roleService.getRoleListByAdminId(adminId);
        if (CollUtil.isEmpty(roleList)){
            return permissions;
        }
        permissions.addAll(roleList.stream().map(Role::getCode).toList());
        Set<Long> menuIds = Sets.newHashSet();
        List<RoleMenuRef> roleMenuRefs = roleMenuRefService.list(Wrappers.<RoleMenuRef>query().lambda().in(RoleMenuRef::getRoleId, roleList.stream().map(Role::getId).toList()));
        menuIds.addAll(roleMenuRefs.stream().map(RoleMenuRef::getMenuId).collect(Collectors.toSet()));
        if (CollUtil.isEmpty(menuIds)){
            return permissions;
        }
        List<Menu> menus = list(Wrappers.<Menu>lambdaQuery().in(Menu::getId, menuIds)
                .orderByDesc(Menu::getWeight));
        permissions.addAll(menus.stream().map(Menu::getPermission).filter(StrUtil::isNotBlank).toList());
        return permissions;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addMenu(Menu menu) {
        Long adminId = AuthUtil.getAdminId();
        verifyMenu(menu,0L);
        boolean save = save(menu);
        List<Role> roleList = roleService.getRoleListByAdminId(adminId);
        if (CollUtil.isEmpty(roleList)) {
            return save;
        }
        // 给自己分配对应菜单
        // 先清空
        roleMenuRefService.remove(Wrappers.<RoleMenuRef>lambdaQuery().eq(RoleMenuRef::getMenuId, menu.getId()).in(RoleMenuRef::getRoleId, roleList.stream().map(Role::getId).toList()));
        List<RoleMenuRef> roleMenuRefs = Lists.newArrayList();
        for (Role role : roleList) {
            RoleMenuRef roleMenuRef = new RoleMenuRef();
            roleMenuRef.setMenuId(menu.getId());
            roleMenuRef.setRoleId(role.getId());
            roleMenuRefs.add(roleMenuRef);
        }
        return roleMenuRefService.saveBatch(roleMenuRefs);
    }

    @Override
    public boolean updateMenu(Menu menu) {
        if (Objects.isNull(menu.getId())) {
            throw BusinessException.exception("ID不能为空" );
        }
        verifyMenu(menu,menu.getId());
        return updateById(menu);
    }

    private void verifyMenu(Menu menu,Long currentId){
        if (StrUtil.isBlank(menu.getType())) {
            throw BusinessException.exception("类型不能为空" );
        }
        if (!StrUtil.containsAny(menu.getType(), MENU_TYPE_MENU, MENU_TYPE_PERMISSION)) {
            throw BusinessException.exception("菜单类型错误" );
        }
        if (StrUtil.isBlank(menu.getTitle())) {
            throw BusinessException.exception("菜单名称不能为空" );
        }
        if (count(Wrappers.<Menu>lambdaQuery().ne(Menu::getId,currentId).eq(Menu::getTitle, menu.getTitle())) > 0) {
            throw BusinessException.exception("菜单名称重复" );
        }
        if (StrUtil.equals(menu.getType(), MENU_TYPE_MENU)) {
            // 如果是菜单校验必要参数
            if (StrUtil.isBlank(menu.getName())) {
                throw BusinessException.exception("路由名称不能为空" );
            }
            if (count(Wrappers.<Menu>lambdaQuery().ne(Menu::getId,currentId).eq(Menu::getName, menu.getName())) > 0) {
                throw BusinessException.exception("路由名称重复" );
            }
            if (StrUtil.isBlank(menu.getPath())) {
                throw BusinessException.exception("页面路径不能为空" );
            }
            if (count(Wrappers.<Menu>lambdaQuery().ne(Menu::getId,currentId).eq(Menu::getPath, menu.getPath())) > 0) {
                throw BusinessException.exception("页面路径重复" );
            }
        }
        if (Objects.isNull(menu.getParentId())) {
            // 默认根ID
            menu.setParentId(ROOT_MENU_ID);
        }
    }

    private Set<Long> getRoleMenuByRoleIds(List<Long> roleIds) {
        Set<Long> menuIds = Sets.newHashSet();
        List<Role> roleList = roleService.listByIds(roleIds);
        //获取当前用户角色菜单
        List<RoleMenuRef> roleMenuList;
        if (!roleList.isEmpty()) {
            roleMenuList = roleMenuRefService.list(Wrappers.<RoleMenuRef>query().lambda().in(RoleMenuRef::getRoleId, roleList.stream().map(Role::getId).toList()));
            menuIds.addAll(roleMenuList.stream().map(RoleMenuRef::getMenuId).collect(Collectors.toSet()));
        }

        return menuIds;
    }
}
