/*
 * Copyright (C) 2022 Yunhou·Huang  yunhouhuang@gmail.com.
 * All rights reserved.
 * Official Web Site: http://houcloud.com.
 */
package com.houcloud.example.common.logger.service;

import com.houcloud.example.common.logger.RecordLog;

/**
 * <p>
 * 日志插件接口
 * </p>
 *
 * @author <a href="mailto:yunhouhuang@gmail.com">yunhouhuang@gmail.com</a>
 */
public interface ILoggerService {

    /**
     * 日志记录接口，所有使用了@Logger的rest接口方法都会触发此方法
     * @param recordLog ！
     */
    void loggerSave(RecordLog recordLog);
}
