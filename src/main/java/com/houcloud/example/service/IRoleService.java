package com.houcloud.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.houcloud.example.model.entity.Role;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  角色服务类
 * </p>
 *
 * @author HouCloud
 * @since 2023-01-17
 */
public interface IRoleService extends IService<Role> {

    /**
     * 根据管理员ID查询角色列表
     * @param adminId 管理员ID
     * @return List of Role
     */
    List<Role> getRoleListByAdminId(Long adminId);


    Map<Long,List<Role>> getRolesMapByAdminIds(List<Long> adminIds);

}
