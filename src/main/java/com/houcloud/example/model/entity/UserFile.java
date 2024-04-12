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
 * 用户文件
 * </p>
 *
 * @author HouCloud
 * @since 2023-01-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_user_file")
@Schema(name = "UserFile", description = "用户文件")
public class UserFile extends Model<UserFile> {
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

    @Schema(description = "文件名")
    @TableField(fill = FieldFill.DEFAULT)
    private String name;

    @Schema(description = "地址")
    private String url;

    @Schema(description = "后缀名")
    private String suffix;

    @Schema(description = "大小(单位字节)")
    private Long size;

    @Schema(description = "分类ID")
    private Long cateId;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "文件原名")
    private String oldName;

    @Schema(description = "oss桶名称")
    private String bucket;

    @Schema(description = "逻辑删除")
    @TableLogic(value = "null",delval = "now()")
    @JsonIgnore
    private LocalDateTime deletedAt;

    @Override
    public Serializable pkVal() {
        return this.id;
    }
}
