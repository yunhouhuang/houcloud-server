package com.houcloud.example.common.logger.utils;

import cn.hutool.core.util.ObjectUtil;
import com.houcloud.example.common.security.token.store.AuthContext;
import com.houcloud.example.common.security.token.store.ContextUser;
import com.houcloud.example.common.logger.RecordLog;
import com.houcloud.example.utils.IpUtil;
import com.houcloud.example.utils.UserAgentUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

/**
 * @author Houcloud
 */
@Slf4j
@UtilityClass
public class LogUtils {

    public RecordLog getLog() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects
                .requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        return getLog(request);
    }

    public RecordLog getLog(HttpServletRequest request) {
        RecordLog recordLog = new RecordLog();
        recordLog.setUa(UserAgentUtil.getUa(request));
        recordLog.setIp(IpUtil.getRealIp(request));
        recordLog.setHost(request.getRemoteHost());
        recordLog.setUri(request.getRequestURI());
        recordLog.setMethod(request.getMethod());
        setId(recordLog);
        return recordLog;
    }

    /**
     * 获取用户名称
     */
    private void setId(RecordLog recordLog) {
        ContextUser contextUser = AuthContext.getContextUser();
        if (ObjectUtil.isNull(contextUser)) {
            return;
        }
        recordLog.setAdminId(contextUser.getAdminId());
        recordLog.setUserId(contextUser.getUserId());
    }

}
