package com.houcloud.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.houcloud.example.model.entity.Admin;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author HouCloud
 * @since 2023-01-16
 */
public interface IAdminService extends IService<Admin> {

    /**
     * 根据ID集合查询简要信息
     * @param adminIds id集合
     * @return id,nickname,avatar
     */
    Map<Long, Admin> getSimpleInfoMapByIds(List<Long> adminIds);

}
