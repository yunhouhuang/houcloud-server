package com.houcloud.example.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.houcloud.example.common.logger.RecordLog;
import com.houcloud.example.common.logger.service.ILoggerService;
import com.houcloud.example.common.logger.utils.LogUtils;
import com.houcloud.example.mapper.SysLogMapper;
import com.houcloud.example.model.entity.SysLog;
import com.houcloud.example.service.ISysLogService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import static com.houcloud.example.common.constants.Constants.SYSLOG_API;
import static com.houcloud.example.common.constants.Constants.SYSLOG_LOGIN;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author HouCloud
 * @since 2023-01-20
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements ISysLogService, ILoggerService {


    @Override
    public void loggerSave(RecordLog recordLog) {
        SysLog sysLog = BeanUtil.copyProperties(recordLog, SysLog.class);
        sysLog.setType(SYSLOG_API);
        save(sysLog);
    }

    @Override
    public void saveLoginLog(HttpServletRequest request, Long id, String username, boolean isSuccess) {
        RecordLog recordLog = LogUtils.getLog(request);
        SysLog sysLog = BeanUtil.copyProperties(recordLog, SysLog.class);
        sysLog.setTitle(isSuccess ? "登录成功" : "登录错误" );
        recordLog.setTag(isSuccess ? "正常" : "错误" );
        sysLog.setAdminId(id);
        sysLog.setType(SYSLOG_LOGIN);
        save(sysLog);
    }
}
