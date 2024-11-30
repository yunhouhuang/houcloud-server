package com.houcloud.example.controller.admin;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.houcloud.example.common.logger.annotation.Logger;
import com.houcloud.example.common.result.Result;
import com.houcloud.example.common.security.permission.annotation.Permission;
import com.houcloud.example.model.dto.MenuTree;
import com.houcloud.example.model.entity.Menu;
import com.houcloud.example.model.entity.RoleMenuRef;
import com.houcloud.example.model.request.IdBody;
import com.houcloud.example.model.request.PageListParams;
import com.houcloud.example.service.IMenuService;
import com.houcloud.example.service.IRoleMenuRefService;
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
 * 菜单接口
 * </p>
 *
 * @author HouCloud
 * @since 2023-01-17
 */
@Tag(name = "菜单接口")
@RestController
@RequestMapping("/api/admin/menu")
public class MenuController {

    @Resource
    private IMenuService menuService;

    @Resource
    private IRoleMenuRefService roleMenuRefService;

    @Operation(summary = "获取菜单详情")
    @GetMapping
    public Result<Menu> getMenu(@RequestParam Long id) {
        Menu menu = menuService.getById(id);
        if (Objects.isNull(menu)) {
            return Result.notfound("菜单未找到");
        }
        return Result.success(menu);
    }

    @Operation(summary = "获取树形结构")
    @GetMapping("/tree")
    public Result<List<MenuTree>> getMenuTree(Boolean lazy, Boolean all, Long parentId) {
        System.out.println(lazy);
        System.out.println(parentId);
        return Result.success(menuService.getMenuTree(lazy, all, parentId));
    }

    @Operation(summary = "获取菜单列表")
    @GetMapping("/list")
    public Result<Page<Menu>> getMenuList(PageListParams params, String type) {
        LambdaQueryWrapper<Menu> wrapper = Wrappers.<Menu>lambdaQuery().orderByDesc(Menu::getCreatedAt);
        if (StrUtil.isNotBlank(type)) {
            wrapper.eq(Menu::getType, type);
        }
        if (StrUtil.isNotBlank(params.getKeywords())) {
            wrapper.like(Menu::getTitle, params.getKeywords()).or(r ->
                    r.like(Menu::getName, params.getKeywords())
            ).or(r ->
                    r.like(Menu::getPath, params.getKeywords())
            );
        }
        Page<Menu> menuPage = menuService.page(Page.of(params.getPage(), params.getLimit()), wrapper);
        return Result.success(menuPage);
    }


    @PostMapping
    @Operation(summary = "添加菜单")
    @Logger(value = "添加菜单")
    @Permission(value = "menu:add", name = "添加菜单")
    @Transactional(rollbackFor = Exception.class)
    public Result<Menu> addMenu(@RequestBody Menu menu) {
        return menuService.addMenu(menu) ? Result.success(menu) : Result.fail();
    }

    @Logger(value = "更新菜单")
    @Operation(summary = "更新菜单")
    @PutMapping
    @Permission(value = "menu:update", name = "更新菜单")
    public Result<Menu> updateMenu(@RequestBody Menu menu) {
        return menuService.updateMenu(menu) ? Result.success(menu) : Result.fail();
    }

    @Logger(value = "删除菜单")
    @Operation(summary = "删除菜单")
    @DeleteMapping
    @Permission(value = "menu:delete", name = "删除菜单")
    @Transactional(rollbackFor = Exception.class)
    public Result<Menu> deleteMenu(@Valid @RequestBody IdBody<Long> idBody) {
        Menu menu = menuService.getById(idBody.getId());
        if (Objects.isNull(menu)) {
            return Result.fail("菜单不存在");
        }
        // 是否含有子节点
        long count = menuService.count(Wrappers.<Menu>lambdaQuery().eq(Menu::getParentId, menu.getId()));
        if (count > 0) {
            return Result.fail("请先删除子菜单");
        }
        boolean remove = menuService.removeById(menu);
        boolean remove1 = roleMenuRefService.remove(Wrappers.<RoleMenuRef>lambdaQuery().eq(RoleMenuRef::getMenuId, menu.getId()));
        return remove && remove1 ? Result.success(menu) : Result.fail();
    }
}
