package com.houcloud.example.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.houcloud.example.mapper.AdminMapper;
import com.houcloud.example.model.entity.Admin;
import com.houcloud.example.service.IAdminService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author HouCloud
 * @since 2023-01-16
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Override
    public Map<Long, Admin> getSimpleInfoMapByIds(List<Long> adminIds) {
        List<Admin> list = list(Wrappers.<Admin>lambdaQuery().in(Admin::getId, adminIds));
        return list.stream().collect(Collectors.toMap(Admin::getId, Function.identity(), (k1, k2) -> k1));
    }
}
