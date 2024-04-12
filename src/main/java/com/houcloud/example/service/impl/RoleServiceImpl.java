package com.houcloud.example.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.houcloud.example.mapper.RoleMapper;
import com.houcloud.example.model.entity.AdminRoleRef;
import com.houcloud.example.model.entity.Role;
import com.houcloud.example.service.IAdminRoleRefService;
import com.houcloud.example.service.IRoleService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色服务实现类
 * </p>
 *
 * @author HouCloud
 * @since 2023-01-17
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Resource
    private IAdminRoleRefService adminRoleRefService;

    @Override
    public List<Role> getRoleListByAdminId(Long adminId) {
        List<AdminRoleRef> adminRoleRefs = adminRoleRefService.list(Wrappers.<AdminRoleRef>lambdaQuery().eq(AdminRoleRef::getAdminId, adminId));
        if (CollUtil.isEmpty(adminRoleRefs)) {
            return Collections.emptyList();
        }
        return list(Wrappers.<Role>query().lambda().in(Role::getId, adminRoleRefs.stream().map(AdminRoleRef::getRoleId).toList()));
    }

    @Override
    public Map<Long, List<Role>> getRolesMapByAdminIds(List<Long> adminIds) {
        List<AdminRoleRef> roleRefList = adminRoleRefService.lambdaQuery().in(AdminRoleRef::getAdminId, adminIds).list();
        HashMap<Long, List<Role>> hashMap = MapUtil.newHashMap();
        if (CollUtil.isEmpty(roleRefList)){
            return hashMap;
        }
        List<Role> roles = listByIds(roleRefList.stream().map(AdminRoleRef::getRoleId).toList());
        Map<Long, Role> roleMap = roles.stream().collect(Collectors.toMap(Role::getId, Function.identity()));
        for (AdminRoleRef adminRoleRef : roleRefList) {
            if (Objects.isNull(roleMap.get(adminRoleRef.getRoleId()))){
                continue;
            }
            if (CollUtil.isEmpty(hashMap.get(adminRoleRef.getAdminId()))){
                hashMap.put(adminRoleRef.getAdminId(),CollUtil.newArrayList(roleMap.get(adminRoleRef.getRoleId())));
            }else {
                hashMap.get(adminRoleRef.getAdminId()).add(roleMap.get(adminRoleRef.getRoleId()));
            }
        }
        return hashMap;
    }
}
