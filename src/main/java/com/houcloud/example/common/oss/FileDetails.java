package com.houcloud.example.common.oss;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 上传文件后的返回对象
 * </p>
 *
 * @author Houcloud@foxmail.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "文件上传返回对象")
public class FileDetails implements Serializable {

    /**
     * 文件名
     */
    @Schema(description = "文件名")
    private String fileName;

    /**
     * 文件桶
     */
    @Schema(description =  "文件桶名称")
    private String bucket;

    /**
     * 文件url
     */
    @Schema(description =  "文件url")
    private String url;
}
