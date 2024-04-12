/*
 * Copyright (C) 2022 Yunhou·Huang  yunhouhuang@gmail.com.
 * All rights reserved.
 * Official Web Site: http://houcloud.com.
 */

package com.houcloud.example.utils;

import cn.hutool.http.useragent.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * mark
 * </p>
 *
 * @author <a href="mailto:yunhouhuang@gmail.com">yunhouhuang@gmail.com</a>
 */
@Slf4j
public class UserAgentUtil {

    public  static String getUa(HttpServletRequest request){
        String header = request.getHeader("user-agent");
        try {
            UserAgent parse = UserAgentParser.parse(header);
            Browser browser = parse.getBrowser();
            OS os = parse.getOs();
            Platform platform = parse.getPlatform();
            String osVersion = parse.getOsVersion();
            return os + "|" + osVersion + "|" + platform + "|" + browser;
        } catch (Exception e) {
            log.info("获取请求系统/浏览器信息失败");
        }
        return "unknown";
    }
}
