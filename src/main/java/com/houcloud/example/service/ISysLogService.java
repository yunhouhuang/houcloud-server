package com.houcloud.example.service;

import com.houcloud.example.model.entity.SysLog;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author HouCloud
 * @since 2023-01-20
 */
public interface ISysLogService extends IService<SysLog> {

    void saveLoginLog(HttpServletRequest request, Long id, String username,boolean isSuccess);
}
