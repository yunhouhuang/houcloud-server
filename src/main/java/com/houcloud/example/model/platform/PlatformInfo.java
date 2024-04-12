/*
 * Copyright (C) 2022 Yunhou·Huang  yunhouhuang@gmail.com.
 * All rights reserved.
 * Official Web Site: http://houcloud.com.
 */

package com.houcloud.example.model.platform;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * 平台信息
 * </p>
 *
 * @author <a href="mailto:yunhouhuang@gmail.com">yunhouhuang@gmail.com</a>
 */
@Data
@Schema(description = "平台信息")
public class PlatformInfo {

    @Schema(description = "logo")
    private String logo;

    @Schema(description = "平台名称")
    private String name;

    @Schema(description = "联系地址")
    private String address;

    @Schema(description = "企业口号/标语")
    private String subtitle;

    @Schema(description = "企业说明")
    private String intro;

    @Schema(description = "官网地址")
    private String officialUrl;

    @Schema(description = "联系电话")
    private String phone;

    @Schema(description = "ICP备案")
    private String icp;

    @Schema(description = "系统版本")
    private String sysVersion;

    @Schema(description = "统一社会信用代码")
    private String uscCode;

    @Schema(description = "营业执照（图片）")
    private String businessLicense;

}
