package com.houcloud.example.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author Houcloud
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MenuTree extends TreeNode {
    @Schema(description = "行创建时间" )
    private LocalDateTime createdAt;

    @Schema(description = "行更新时间" )
    private LocalDateTime updatedAt;

    @Schema(description = "菜单名称" )
    private String title;

    @Schema(description = "前端路由Name" )
    private String name;

    @Schema(description = "权限编码" )
    private String permission;

    @Schema(description = "前端跳转路径" )
    private String path;

    @Schema(description = "上级ID" )
    private Long parentId;

    @Schema(description = "菜单图标" )
    private String icon;

    @Schema(description = "排序权重（越大越优先）" )
    private Long weight;

    @Schema(description = "是否持久页" )
    private Boolean keepAlive;

    @Schema(description = "类型（1按钮 0菜单）" )
    private String type;

    @Schema(description = "是否白名单" )
    private Boolean white;

    @Schema(description = "是否隐藏" )
    private Boolean hidden;

    @Schema(description = "是否隐藏子菜单")
    private Boolean single;

    @Schema(description = "前端组件地址")
    private String component;

    @Schema(description = "是否展开" )
    private boolean spread = false;

    @Schema(description = "重定向")
    private String redirect;


    private String label;

    private Long value;

}
