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
 * 管理员
 * </p>
 *
 * @author yunhouhuang@gmail.com
 * @since 2023-02-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_admin")
@Schema(name = "Admin", description = "管理员")
public class Admin extends Model<Admin> {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long creatorId;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "手机号")
    private String mobile;

    @Schema(description = "用户密码(密文)")
    private String password;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "行创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @Schema(description = "行更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    @Schema(description = "最后活跃时间")
    private LocalDateTime lastLiveAt;
    @Schema(description = "是否创始人")
    private Boolean isCreator;

    @Schema(description = "是否已锁定")
    private Boolean locked;
    @Schema(description = "逻辑删除")
    @TableLogic(value = "null",delval = "now()")
    @JsonIgnore
    private LocalDateTime deletedAt;

    @Override
    public Serializable pkVal() {
        return this.id;
    }
}
