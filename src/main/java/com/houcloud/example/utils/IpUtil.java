package com.houcloud.example.utils;


import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.Objects;


/**
 * @author Houcloud
 */
@Slf4j
@Getter
@Setter
public class IpUtil {
    private static final long serialVersionUID = -6246313701294144575L;
    private String area;
    private String country;
    private String isp_id;
    private String queryIp;
    private String city;
    private String ip;
    private String isp;
    private String county;
    private String region_id;
    private String area_id;
    private String county_id;
    private String region;
    private String country_id;
    private String city_id;

    // 静态常量
    public static final String UNKNOWN = "unknown" ;
    public static final String IP = "0:0:0:0:0:0:0:1" ;
    public static final String BLANK_IP = "0.0.0.0" ;
    public static final String SP = "," ;
    public static final String EP = "." ;
    public static final String NGINX_CUSTOM_IP_KEY = "X-Real-IP" ;
    private static final String LOCALHOST = "127.0.0.1" ;
    private static final String HEADER_X_FORWARDED_FOR = "X-Forwarded-For" ;
    private static final String HTTP_X_FORWARDED_FOR = "HTTP_X_FORWARDED_FOR" ;
    private static final String HEADER_PROXY_CLIENT_IP = "Proxy-Client-IP" ;
    private static final String HEADER_WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP" ;
    private static final String HEADER_PORT = "Port" ;
    private static final String HEADER_HOST = "Host" ;

    public static String getRealIp(HttpServletRequest request) {
        String ip = request.getHeader(NGINX_CUSTOM_IP_KEY);
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = BLANK_IP;
        }
        return ip;
    }

    public static void checkIpLog(ServerHttpRequest request) {
        String ip = request.getHeaders().getFirst(NGINX_CUSTOM_IP_KEY);
        log.info("NGINX_CUSTOM_IP_KEY:{}\n", ip);
        ip = request.getHeaders().getFirst(HTTP_X_FORWARDED_FOR);
        log.info("HTTP_X_FORWARDED_FOR:{}\n", ip);
        ip = request.getHeaders().getFirst(HEADER_PROXY_CLIENT_IP);
        log.info("HEADER_PROXY_CLIENT_IP:{}\n", ip);
        ip = request.getHeaders().getFirst(HTTP_X_FORWARDED_FOR);
        log.info("HTTP_X_FORWARDED_FOR:{}\n", ip);
        ip = request.getHeaders().getFirst(HEADER_WL_PROXY_CLIENT_IP);
        log.info("HEADER_WL_PROXY_CLIENT_IP:{}\n", ip);
        log.info("getRemoteAddress:{}\n", request.getRemoteAddress());
        log.info("HEADER_HOST:{}\n", request.getHeaders().getFirst(HEADER_HOST));
        log.info("HEADER_PORT:{}\n", request.getHeaders().getFirst(HEADER_PORT));
        log.info("getLocalAddress:{}\n", request.getLocalAddress());
        log.info("getHost:{}\n", request.getHeaders().getHost());

        log.info("{}\n", request.getURI().toString());

        log.info("{}\n", request.getMethod());
    }

    public static String getDomain(HttpServletRequest request) {
        String remoteHost = request.getRemoteHost();
        log.info("RemoterHost：「{}」", remoteHost);
        return remoteHost;
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (checkIsIp(ip)) {
            return ip;
        }

        ip = request.getHeader("X-Real-IP");
        if (checkIsIp(ip)) {
            return ip;
        }

        ip = request.getRemoteAddr();
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            //本地 localhost访问 ipv6
            ip = "127.0.0.1" ;
        }
        if (checkIsIp(ip)) {
            return ip;
        }

        return "" ;
    }

    /**
     * 检测是否为ip
     *
     * @param ip 参数
     * @return String
     * @author Houcloud
     * @since 2021-08-22
     */
    public static boolean checkIsIp(String ip) {
        if (StrUtil.isBlank(ip)) {
            return false;
        }

        if ("unKnown".equals(ip)) {
            return false;
        }

        if ("unknown".equals(ip)) {
            return false;
        }

        return ip.split("\\.").length == 4;
    }


    /**
     * 通过 IP 获取地址 (淘宝接口)
     *
     * @param ip {@code String} 用户 IP 地址
     * @return {@code String} 用户地址
     */
    public static IpUtil getIpInfo(String ip) {
        if (LOCALHOST.equals(ip)) {
            ip = LOCALHOST;
        }
        try {
            URL url = new URL("https://ip.taobao.com/outGetIpInfo?accessKey=alibaba-inc&ip=" + ip);
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setRequestMethod("GET");
            httpCon.setDoOutput(true);
            httpCon.setDoInput(true);
            httpCon.setUseCaches(false);

            InputStream in = httpCon.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            StringBuilder temp = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null) {
                temp.append(line).append("\r\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            return JacksonUtils.json2pojoByTree(temp.toString(), "data", IpUtil.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new IpUtil();
        }
    }


    /**
     * 获取真实客户端IP
     *
     * @param serverHttpRequest s
     * @return
     */
    public static String getRealIpAddress(ServerHttpRequest serverHttpRequest) {
        String ipAddress;
        try {
            ipAddress = serverHttpRequest.getHeaders().getFirst(NGINX_CUSTOM_IP_KEY);
            if (StrUtil.isBlank(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = serverHttpRequest.getHeaders().getFirst(HEADER_X_FORWARDED_FOR);
            }
            if (StrUtil.isBlank(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = serverHttpRequest.getHeaders().getFirst(HTTP_X_FORWARDED_FOR);
            }
            if (StrUtil.isBlank(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = serverHttpRequest.getHeaders().getFirst(HEADER_PROXY_CLIENT_IP);
            }
            if (StrUtil.isBlank(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = serverHttpRequest.getHeaders().getFirst(HEADER_WL_PROXY_CLIENT_IP);
            }
            if (StrUtil.isBlank(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                InetSocketAddress inetSocketAddress = serverHttpRequest.getRemoteAddress();
                if (Objects.isNull(inetSocketAddress)) {
                    log.info("获取IP地址失败");
                    return BLANK_IP;
                }
                ipAddress = inetSocketAddress.getAddress().getHostAddress();
                if (LOCALHOST.equals(ipAddress)) {
                    InetAddress localAddress = InetAddress.getLocalHost();
                    if (Objects.nonNull(localAddress.getHostAddress())) {
                        ipAddress = localAddress.getHostAddress();
                    }
                }
            }
            if (StrUtil.isNotBlank(ipAddress)) {
                ipAddress = ipAddress.split(SP)[0].trim();
            }
        } catch (Exception e) {
            log.error("解析请求IP地址失败", e);
            ipAddress = BLANK_IP;
        }
        return StrUtil.isBlank(ipAddress) ? BLANK_IP : ipAddress;
    }
}
