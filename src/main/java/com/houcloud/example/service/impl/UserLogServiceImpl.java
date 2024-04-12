package com.houcloud.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.houcloud.example.mapper.UserLogMapper;
import com.houcloud.example.model.entity.UserLog;
import com.houcloud.example.service.IUserLogService;
import com.houcloud.example.utils.IpUtil;
import com.houcloud.example.utils.UserAgentUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static com.houcloud.example.common.constants.Constants.SYSLOG_API;
import static com.houcloud.example.common.constants.Constants.SYSLOG_LOGIN;

/**
 * <p>
 * 用户日志 服务实现类
 * </p>
 *
 * @author HouCloud
 * @since 2023-01-24
 */
@Service
public class UserLogServiceImpl extends ServiceImpl<UserLogMapper, UserLog> implements IUserLogService {

    @Async
    @Override
    public void saveLoginLog(String title, String content, HttpServletRequest request){
        buildLog(title, content, request, SYSLOG_LOGIN);
    }

    @Async
    @Override
    public void saveRequestLog(String title, String content, HttpServletRequest request){
        buildLog(title, content, request, SYSLOG_API);
    }

    private void buildLog(String title, String content, HttpServletRequest request, String type) {
        UserLog userLog = new UserLog();
        userLog.setUa(UserAgentUtil.getUa(request));
        userLog.setIp(IpUtil.getRealIp(request));
        userLog.setUri(request.getRequestURI());
        userLog.setMethod(request.getMethod());
        userLog.setHost(request.getRemoteHost());
        userLog.setType(type);
        userLog.setContent(content);
        userLog.setTitle(title);
    }


}
