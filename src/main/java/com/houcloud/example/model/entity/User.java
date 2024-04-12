package com.houcloud.example.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author HouCloud
 * @since 2023-01-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_user")
@Schema(name = "User", description = "用户")
public class User extends Model<User> {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "用户密码(密文)")
    private String password;

    @Schema(description = "用户密码(密文)")
    private String payPassword;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "微信小程序openid")
    private String wechatOpenid;

    @Schema(description = "行创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @Schema(description = "最近活跃时间")
    private LocalDateTime lastLiveAt;

    @Schema(description = "行更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    @Schema(description = "手机号")
    private String mobile;

    @Schema(description = "电子邮箱")
    private String email;

    @Schema(description = "钱包余额 CNY（分）")
    private Long wallet;

    @Schema(description = "是否提示钱包红点标记")
    private Boolean walletMark;

    @Schema(description = "1男2女")
    private String sex;

    @Schema(description = "生日")
    private LocalDateTime birthday;

    @Schema(description = "邀请人ID")
    private Long inviteId;

    @Schema(description = "是否黑名单")
    private Boolean blacklist;

    @Schema(description = "是否已锁定")
    private Boolean locked;

    @Schema(description = "身份证号（实名信息）")
    private String idCardNo;

    @Schema(description = "真实姓名（实名信息）")
    private String realName;

    @Schema(description = "逻辑删除")
    @TableLogic(value = "null",delval = "now()")
    @JsonIgnore
    private LocalDateTime deletedAt;

    @Override
    public Serializable pkVal() {
        return this.id;
    }
}
