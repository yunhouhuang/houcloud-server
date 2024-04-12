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
 * 用户消息通知
 * </p>
 *
 * @author HouCloud
 * @since 2023-01-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_user_notice")
@Schema(name = "UserNotice", description = "用户消息通知")
public class UserNotice extends Model<UserNotice> {
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

    @Schema(description = "通知标题")
    private String title;

    @Schema(description = "通知内容")
    private String content;

    @Schema(description = "状态 0 未读 1 已读")
    private String status;

    @Schema(description = "关联类型")
    private String refType;

    @Schema(description = "关联ID")
    private Long refId;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "是否全局消息")
    private Boolean global;

    @Schema(description = "逻辑删除")
    @TableLogic(value = "null",delval = "now()")
    @JsonIgnore
    private LocalDateTime deletedAt;

    @Override
    public Serializable pkVal() {
        return this.id;
    }
}
