package com.houcloud.example.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.io.Serial;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 菜单
 * </p>
 *
 * @author HouCloud
 * @since 2023-01-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_menu")
@Schema(name = "Menu", description = "菜单")
public class Menu extends Model<Menu> {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "行创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @Schema(description = "行更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    @Schema(description = "菜单名称")
    private String title;

    @Schema(description = "前端路由Name")
    @TableField(fill = FieldFill.DEFAULT)
    private String name;

    @Schema(description = "权限编码")
    private String permission;

    @Schema(description = "前端跳转路径")
    private String path;

    @Schema(description = "上级ID")
    private Long parentId;

    @Schema(description = "菜单图标")
    private String icon;

    @Schema(description = "排序权重（越大越优先）")
    private Long weight;

    @Schema(description = "是否持久页")
    private Boolean keepAlive;

    @Schema(description = "类型（1按钮 0菜单）")
    private String type;

    @Schema(description = "是否白名单")
    private Boolean white;

    @Schema(description = "是否隐藏")
    private Boolean hidden;

    @Schema(description = "是否隐藏子菜单")
    private Boolean single;

    @Schema(description = "前端组件地址")
    private String component;

    @Schema(description = "重定向")
    private String redirect;

    @Schema(description = "逻辑删除")
    @TableLogic(value = "null",delval = "now()")
    @JsonIgnore
    private LocalDateTime deletedAt;

    @Override
    public Serializable pkVal() {
        return this.id;
    }
}
