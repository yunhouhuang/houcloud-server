package com.houcloud.example.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 管理员角色关联
 * </p>
 *
 * @author HouCloud
 * @since 2023-01-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_admin_role_ref")
@Schema(name = "AdminRoleRef", description = "管理员角色关联")
public class AdminRoleRef extends Model<AdminRoleRef> {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "管理员ID")
    private Long adminId;

    @Schema(description = "角色ID")
    private Long roleId;

    @Override
    public Serializable pkVal() {
        return this.roleId;
    }
}
