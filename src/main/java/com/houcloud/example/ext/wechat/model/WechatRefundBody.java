package com.houcloud.example.ext.wechat.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description = "调用微信退款所需要的参数")
public class WechatRefundBody {

    @Schema(description = "appId，公众号名称，由商户传入", nullable = true)
    private String appid;

    @Schema(description = "直连商户的商户号，由微信支付生成并下发", nullable = true)
    private String mch_id;

    @Schema(description = "随机字符串，不长于32位", nullable = true)
    private String nonce_str;

    @Schema(description = "签名", nullable = true)
    private String sign;

    @Schema(description = "签名类型，目前支持HMAC-SHA256和MD5，默认为MD5")
    private String sign_type = "MD5";

    @Schema(description = "微信支付订单号:微信生成的订单号，在支付通知中有返回")
    private String transaction_id;

    @Schema(description = "商户系统内部的订单号,32个字符内、可包含字母, 其他说明见商户订单号", nullable = true)
    private String out_trade_no;

    @Schema(description = "商户退款单号,同一退款单号多次请求只退一笔。")
    private String out_refund_no;

    @Schema(description = "订单总金额，单位为分", nullable = true)
    private int total_fee;

    @Schema(description = "退款金额，单位为分", nullable = true)
    private int refund_fee;

    @Schema(description = "退款货币种类:符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型")
    private String refund_fee_type = "CNY";

    @Schema(description = "退款结果通知url")
    private String notify_url;

}