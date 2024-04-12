package com.houcloud.example.ext.wechat.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="企业打款到零钱")
public class ToUserWalletBody {

    @Schema(description="appId，公众号名称，由商户传入", nullable = true)
    private String appid;

    @Schema(description="直连商户的商户号，由微信支付生成并下发", nullable = true)
    private String mch_id;

    @Schema(description="随机字符串，不长于32位", nullable = true)
    private String nonce_str;

    @Schema(description="签名", nullable = true)
    private String sign;

    @Schema(description="签名类型，目前支持HMAC-SHA256和MD5，默认为MD5")
    private String sign_type = "MD5";

    @Schema(description="openid", nullable = true)
    private String openid;

    @Schema(description="转账交易单号")
    private String partner_trade_no;

    @Schema(description="转账金额，单位为分", nullable = true)
    private int amount;


    @Schema(description="ip")
    private String spbill_create_ip;

    @Schema(description="转账备注")
    private String desc;

    @Schema(description="校验姓名")
    private String check_name;

}