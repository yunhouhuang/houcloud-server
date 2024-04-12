package com.houcloud.example.ext.wechat.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "微信统一下单对象")
public class CreateOrderRequestBody {
    @Schema(description = "appId，公众号名称，由商户传入", nullable = true)
    private String appid;

    @Schema(description = "直连商户的商户号，由微信支付生成并下发", nullable = true)
    private String mch_id;

    @Schema(description = "终端设备号(门店号或收银设备ID)，注意：PC网页或公众号内支付请传 WEB")
    private String device_info = "WEB";

    @Schema(description = "随机字符串，不长于32位", nullable = true)
    private String nonce_str;

    @Schema(description = "签名", nullable = true)
    private String sign;

    @Schema(description = "签名类型，默认为MD5，支持HMAC-SHA256和MD5。")
    private String sign_type;

    @Schema(description = "商品简单描述，该字段须严格按照规范传递", nullable = true)
    private String body;

    @Schema(description = "商品简单描述")
    private String detail;

    @Schema(description = "附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据, String(127)")
    private String attach;

    @Schema(description = "商户系统内部的订单号,32个字符内、可包含字母, 其他说明见商户订单号", nullable = true)
    private String out_trade_no;

    @Schema(description = "符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型")
    private String fee_type = "CNY";

    @Schema(description = "订单总金额，单位为分", nullable = true)
    private int total_fee;

    @Schema(description = "必须传正确的用户端IP,支持ipv4、ipv6格式，获取方式详见获取用户ip指引", nullable = true)
    private String spbill_create_ip;

    @Schema(description = "订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则")
    private String time_start;

    @Schema(description = "订单失效时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则")
    private String time_expire;

    @Schema(description = "商品标记，代金券或立减优惠功能的参数，说明详见代金券或立减优惠")
    private String goods_tag;

    @Schema(description = "接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。")
    private String notify_url;

    @Schema(description = "JSAPI -JSAPI支付（或小程序支付）， NATIVE -Native支付， APP -APP支付，MWEB--H5支付", nullable = true)
    private String trade_type;

    @Schema(description = "trade_type=NATIVE，此参数必传。此id为二维码中包含的商品ID，商户自行定义。")
    private String product_id;

    @Schema(description = "no_credit--指定不能使用信用卡支付")
    private String limit_pay;

    @Schema(description = "trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识。openid如何获取，可参考【获取openid】。企业号请使用【企业号OAuth2.0接口】获取企业号内成员userid，再调用【企业号userid转openid接口】进行转换")
    private String openid;

    @Schema(description = "Y，传入Y时，支付成功消息和支付详情页将出现开票入口。需要在微信支付商户平台或微信公众平台开通电子发票功能，传此字段才可生效")
    private String receipt = "N";

    @Schema(description = "该字段用于上报支付的场景信息,针对H5支付有以下三种场景,请根据对应场景上报,H5支付不建议在APP端使用，针对场景1，2请接入APP支付，不然可能会出现兼容性问题")
    private String scene_info;

}