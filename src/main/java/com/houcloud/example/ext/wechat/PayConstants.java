package com.houcloud.example.ext.wechat;

/**
 *  支付相关常量类
 */
public class PayConstants {

    //支付方式
    public static final String PAY_TYPE_YUE = "yue";
    public static final String PAY_TYPE_WECHAT = "wechat";

    // 积分付款
    public static final String INTEGRAL = "integral";

    //支付渠道
    public static final String PAY_CHANNEL_WE_CHAT_H5 = "weixinh5"; //H5唤起微信支付
    public static final String PAY_CHANNEL_WE_CHAT_PUBLIC = "public"; //公众号
    public static final String PAY_CHANNEL_WE_CHAT_PROGRAM = "routine"; //小程序
    public static final String PAY_CHANNEL_WE_CHAT_APP_IOS = "weixinAppIos"; //微信App支付ios
    public static final String PAY_CHANNEL_WE_CHAT_APP_ANDROID = "weixinAppAndroid"; //微信App支付android

    public static final String WX_PAY_TRADE_TYPE_JS = "JSAPI";
    public static final String WX_PAY_TRADE_TYPE_H5 = "MWEB";

    //微信支付接口请求地址
    public static final String WX_PAY_API_URL = "https://api.mch.weixin.qq.com/";
    // 微信统一预下单
    public static final String WX_PAY_API_URI = "pay/unifiedorder";
    // 微信查询订单
    public static final String WX_PAY_ORDER_QUERY_API_URI = "pay/orderquery";
    // 微信支付回调地址
    public static final String WX_PAY_NOTIFY_API_URI = "/api/callback/payment/wechat";
    // 微信退款回调地址
    public static final String WX_PAY_REFUND_NOTIFY_API_URI = "/api/callback/wechat/refund";

    public static final String WX_PAY_SIGN_TYPE_MD5 = "MD5";
    public static final String WX_PAY_SIGN_TYPE_SHA256 = "HMAC-SHA256";

    public static final String PAY_BODY = "HOUCLOUD收银中心";
    public static final String FIELD_SIGN = "sign";

    // 公共号退款
    public static final String WX_PAY_REFUND_API_URI= "secapi/pay/refund";

    public static final String PAY_TYPE_WE_CHAT = "PAY_TYPE_WE_CHAT";

    public static final String FAIL     = "FAIL";
    public static final String SUCCESS  = "SUCCESS";

}
