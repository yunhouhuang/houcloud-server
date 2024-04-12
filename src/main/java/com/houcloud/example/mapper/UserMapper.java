package com.houcloud.example.mapper;

import com.houcloud.example.model.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 用户 Mapper 接口
 * </p>
 *
 * @author HouCloud
 * @since 2023-01-16
 */
public interface UserMapper extends BaseMapper<User> {

    @Update("UPDATE `t_user` SET `wallet` = wallet - #{amount} WHERE `id` = #{userId} AND (wallet - #{amount}) >= 0 ")
    int subWallet(Long userId, Long amount);

    @Update("UPDATE `t_user` SET `wallet` = COALESCE(wallet, 0) + #{amount} WHERE `id` = #{userId}")
    int addWallet(Long userId, Long amount);
}
