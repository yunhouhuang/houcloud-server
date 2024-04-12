package com.houcloud.example.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户收藏
 * </p>
 *
 * @author HouCloud
 * @since 2023-01-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_user_collect")
@Schema(name = "UserCollect", description = "用户收藏")
public class UserCollect extends Model<UserCollect> {
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

    @Schema(description = "标题")
    private String title;

    @Schema(description = "封面图")
    private String cover;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "商品ID（内部表）")
    private Long productId;

    @Schema(description = "添加时佣金比")
    private BigDecimal ddxCosRatio;

    @Schema(description = "添加时佣金")
    private Long ddxCosFee;

    @Override
    public Serializable pkVal() {
        return this.id;
    }
}
