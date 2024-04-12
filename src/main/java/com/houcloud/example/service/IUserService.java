package com.houcloud.example.service;

import com.houcloud.example.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

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
public interface IUserService extends IService<User> {

    /**
     * 通过微信小程序openid查找
     * @param waOpenid
     * @return
     */
    User getByWechatAppletOpenid(String waOpenid);

    /**
     * 扣除余额
     * @param userId 用户
     * @param amount 扣除金额 （正数）
     * @param description 用途说明
     * @return status
     */
    boolean subWallet(Long userId, Long amount,String description);

    /**
     * 增加余额
     * @param userId 用户
     * @param amount 增加的金额（正数）
     * @param description 来源说明
     * @return status
     */
    boolean addWallet(Long userId, Long amount,String description);

    Map<Long, User> getMapByIds(List<Long> userIds);
}
