package com.houcloud.example.common.logger.aspect;


import com.houcloud.example.common.logger.RecordLog;
import com.houcloud.example.common.logger.event.LogRecordEvent;
import com.houcloud.example.common.logger.utils.LogUtils;
import com.houcloud.example.common.logger.annotation.Logger;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationEventPublisher;

/**
 * <p> 记录日志切面 </p>
 *
 * @author <a href="mailto:yunhouhuang@gmail.com">yunhouhuang@gmail.com</a>
 */
@Slf4j
@Aspect
public class LogRecordAspect {

    private final ApplicationEventPublisher publisher;

    public LogRecordAspect(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @SneakyThrows
    @Around("@annotation(logger)")
    public Object around(ProceedingJoinPoint point, Logger logger) {
        String className = point.getTarget().getClass().getName();
        String methodName = point.getSignature().getName();
        log.info("[类名]:{},[方法]:{}", className, methodName);
        RecordLog recordLog = LogUtils.getLog();
        recordLog.setTag("正常");
        recordLog.setTitle(logger.value());
        Object obj = point.proceed();
        if (logger.console()) {
            log.info("""
                    
                    --------------------------------CONSOLE LOG----------------------------------
                     {}\s
                    -----------------------------------------------------------------------------""", recordLog);
        }
        // 发送异步日志事件
        publisher.publishEvent(new LogRecordEvent(recordLog));
        return obj;
    }

}
