package com.houcloud.example.common.notice.dingtalk;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.houcloud.example.common.notice.MessageConfiguration;
import com.taobao.api.ApiException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * <p> 钉钉通知处理器 </p>
 *
 * @author Houcloud
 */
@Slf4j
@Component
public class DingTalkMessage {

    private static MessageConfiguration messageConfiguration;

    @Resource
    public void setMessageConfiguration(MessageConfiguration messageConfiguration) {
        DingTalkMessage.messageConfiguration = messageConfiguration;
    }

    private static void setTextMessage(String content, DingTalkClient client, OapiRobotSendRequest request) throws ApiException {
        if (!messageConfiguration.getDingtalk().getEnabled()){
            log.info("\n未开启钉钉消息通知: \n{}",content);
            return;
        }
        request.setMsgtype("text");
        OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
        text.setContent(content);
        request.setText(text);
        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        request.setAt(at);
        client.execute(request);
    }

    public static void sendTextDingTalkMessage(String url, String content) {
        if (!messageConfiguration.getDingtalk().getEnabled()){
            log.info("\n未开启钉钉消息通知: \n{}",content);
            return;
        }
        try {
            DingTalkClient client = new DefaultDingTalkClient(url);
            OapiRobotSendRequest request = new OapiRobotSendRequest();
            setTextMessage(content, client, request);
        } catch (Exception e) {
            log.error("钉钉消息发送异常:", e);
        }
    }

    private static String getSign(String secret) throws Exception {
        long timestamp = System.currentTimeMillis();
        String stringToSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
        return URLEncoder.encode(new String(Base64.encodeBase64(signData, false)), StandardCharsets.UTF_8);
    }

    public static void sendExceptionMessage(Exception e) {
        String message = getExceptionStringStackTrace(e);
        sendExceptionMessage(message, e);
    }

    private static String getExceptionStringStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }

    public static void sendMessage(String message) {
        message = "服务运行通知：\n" + message;
        sendTextDingTalkMessage(messageConfiguration.getDingtalk().getHooks(), message);
    }

    public static void sendExceptionMessage(String message, Exception e) {
        message = "系统异常警报：\n" + message + "\n" + getExceptionStringStackTrace(e);
        sendTextDingTalkMessage(messageConfiguration.getDingtalk().getHooks(), message);
    }

    public static void sendExceptionMessage(String message) {
        message = "系统异常警报：\n" + message;
        sendTextDingTalkMessage(messageConfiguration.getDingtalk().getHooks(), message);
    }

    /**
     * 加签消息
     *
     * @param content
     * @param mobiles
     */
    @Async
    public void sendSignTextDingMessage(String url, String secret, String content, List<String> mobiles) {
        try {
            long timestamp = System.currentTimeMillis();
            String sign = getSign(getSign(secret));
            url = "&timestamp=" + timestamp + "&sign=" + sign;
            DingTalkClient client = new DefaultDingTalkClient(url);
            OapiRobotSendRequest request = new OapiRobotSendRequest();
            setTextMessage(content, client, request);
        } catch (Exception e) {
            log.error("钉钉消息发送异常:", e);
        }
    }
}
