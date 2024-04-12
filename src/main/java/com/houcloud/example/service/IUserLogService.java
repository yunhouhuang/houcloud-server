package com.houcloud.example.service;

import com.houcloud.example.model.entity.UserLog;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.scheduling.annotation.Async;

/**
 * <p>
 * 用户日志 服务类
 * </p>
 *
 * @author HouCloud
 * @since 2023-01-24
 */
public interface IUserLogService extends IService<UserLog> {

    @Async
    void saveLoginLog(String title, String content, HttpServletRequest request);

    @Async
    void saveRequestLog(String title, String content, HttpServletRequest request);
}
