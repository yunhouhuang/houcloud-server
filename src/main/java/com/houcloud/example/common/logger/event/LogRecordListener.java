package com.houcloud.example.common.logger.event;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.houcloud.example.common.logger.RecordLog;
import com.houcloud.example.common.logger.service.ILoggerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * <p> 异步监听日志事件 <p/>
 *
 * @author Houcloud
 * @since 2020/5/1 08:02 下午
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LogRecordListener {

    private final Snowflake snowflake = IdUtil.getSnowflake();

    private ILoggerService loggerService;

    @Autowired(required = false)
    public LogRecordListener(ILoggerService loggerService) {
        this.loggerService = loggerService;
    }

    @Order
    @Async
    @EventListener(LogRecordEvent.class)
    public void saveLog(LogRecordEvent event) {
        if (Objects.isNull(loggerService)){
            log.warn("您已使用@Logger但未开启日志记录功能，本次跳过，可实现 ILoggerService 接口方法进行记录。");
            return;
        }
        RecordLog recordLog = event.recordLog();
        if (ObjectUtil.isNull(recordLog)) {
            log.error("收到空日志的监听");
            return;
        }
        loggerService.loggerSave(recordLog);
    }
}
