package com.houcloud.example.service;

import com.houcloud.example.model.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.houcloud.example.model.dto.MenuTree;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

/**
 * <p>
 *  菜单 服务类
 * </p>
 *
 * @author HouCloud
 * @since 2023-01-17
 */
public interface IMenuService extends IService<Menu> {

    /**
     * 异步更新缓存权限
     * @param adminId
     */
    @Async
    void updatePermission(Long adminId);

    /**
     * 获取树形结构菜单
     * @param lazy 是否懒加载模式 true: 不返回子节点 ， false: 返回子节点
     * @param parentId 上级ID
     * @return MenuTree
     */
    List<MenuTree> getMenuTree(Boolean lazy,Boolean all,Long parentId);

    /**
     * 查询管理员的权限编码列表
     * @param adminId 管理员ID
     * @return permissions
     */
    List<String> getAdminPermissions(Long adminId);

    /**
     * 新增菜单
     * @param menu ~
     * @return ~
     */
    Boolean addMenu(Menu menu);

    /**
     * 修改菜单
     * @param menu ~
     * @return ~
     */
    boolean updateMenu(Menu menu);
}
