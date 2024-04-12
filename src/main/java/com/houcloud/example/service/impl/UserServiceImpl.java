package com.houcloud.example.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.houcloud.example.common.exception.BusinessException;
import com.houcloud.example.mapper.UserMapper;
import com.houcloud.example.model.entity.User;
import com.houcloud.example.service.IUserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserMapper userMapper;


    @Override
    public User getByWechatAppletOpenid(String waOpenid) {
        return getOne(Wrappers.<User>lambdaQuery().eq(User::getWechatOpenid, waOpenid));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean subWallet(Long userId, Long amount, String description) {
        if (amount <= 0) {
            throw BusinessException.exception("金额必须大于零");
        }
        User user = getById(userId);
        if (Objects.isNull(user)) {
            throw BusinessException.exception("用户不存在");
        }
        if (user.getWallet() - amount < 0) {
            throw BusinessException.exception("余额不足");
        }
        return userMapper.subWallet(userId, amount) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addWallet(Long userId, Long amount, String description) {
        if (amount <= 0) {
            throw BusinessException.exception("金额必须大于零");
        }
        User user = getById(userId);
        if (Objects.isNull(user)) {
            throw BusinessException.exception("用户不存在");
        }
        return userMapper.addWallet(userId, amount) > 0;
    }

    @Override
    public Map<Long, User> getMapByIds(List<Long> userIds) {
        return lambdaQuery().in(User::getId,userIds).list().stream().collect(Collectors.toMap(User::getId, Function.identity()));
    }
}
