/*
 * Copyright (C) 2022 Yunhou·Huang  yunhouhuang@gmail.com.
 * All rights reserved.
 * Official Web Site: http://houcloud.com.
 */

package com.houcloud.example.controller.admin;

import cn.hutool.core.util.StrUtil;
import com.houcloud.example.common.result.Result;
import com.houcloud.example.common.security.permission.annotation.Permission;
import com.houcloud.example.model.ops.CacheInfo;
import com.houcloud.example.model.ops.ServerInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * <p>
 * 系统维护和监控
 * </p>
 *
 * @author <a href="mailto:yunhouhuang@gmail.com">yunhouhuang@gmail.com</a>
 */
@Tag(name = "维护和监控接口")
@RestController
@RequestMapping("/api/admin/ops")
public class OpsController {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/server")
    @Permission("server:info")
    @Operation(summary = "服务信息")
    public Result<ServerInfo> serverInfo() {
        ServerInfo server = new ServerInfo();
        return Result.success(server.generate());
    }

    @GetMapping("/cache")
    @Permission("cache:info")
    @Operation(summary = "缓存信息")
    public Result<CacheInfo> cacheInfo() {
        Properties info = (Properties) stringRedisTemplate.execute((RedisCallback<Object>) connection -> connection.serverCommands().info());
        Properties commandStats = (Properties) stringRedisTemplate.execute((RedisCallback<Object>) connection -> connection.serverCommands().info("commandstats"));
        Object dbSize = stringRedisTemplate.execute((RedisCallback<Object>) connection -> connection.serverCommands().dbSize());
        CacheInfo cacheInfo = new CacheInfo();
        cacheInfo.setInfo(info);
        cacheInfo.setDbSize(dbSize);
        List<Map<String, String>> pieList = new ArrayList<>();
        if (Objects.nonNull(commandStats)) {
            commandStats.stringPropertyNames().forEach(key -> {
                Map<String, String> data = new HashMap<>(2);
                String property = commandStats.getProperty(key);
                data.put("name", StrUtil.removePrefix(key, "cmdstat_"));
                data.put("value", StrUtil.subBetween(property, "calls=", ",usec"));
                pieList.add(data);
            });
        }
        cacheInfo.setCommandList(pieList);
        return Result.success(cacheInfo);
    }

}
