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
 * 用户日志
 * </p>
 *
 * @author HouCloud
 * @since 2023-01-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_user_log")
@Schema(name = "UserLog", description = "用户日志")
public class UserLog extends Model<UserLog> {
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

    @Schema(description = "日志标题")
    private String title;

    @Schema(description = "日志描述内容")
    private String content;

    @Schema(description = "请求方法")
    private String method;

    @Schema(description = "请求URI")
    private String uri;

    @Schema(description = "请求HOST")
    private String host;

    @Schema(description = "请求UA")
    private String ua;

    @Schema(description = "IP")
    private String ip;

    @Schema(description = "IP地区")
    private String ipAddress;

    @Schema(description = "状态")
    private String tag;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "日志类型 0 接口日志  1 登录日志")
    private String type;

    @Schema(description = "逻辑删除")
    @TableLogic(value = "null",delval = "now()")
    @JsonIgnore
    private LocalDateTime deletedAt;

    @Override
    public Serializable pkVal() {
        return this.id;
    }
}
