package com.houcloud.example.common.logger;

import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.sql.visitor.functions.Char;
import com.google.common.collect.Lists;
import com.houcloud.example.common.logger.utils.LogUtils;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 日志记录对对象
 * </p>
 *
 * @author Houcloud
 * @since 2021/9/2
 */
@Data
public class RecordLog implements Serializable {
    @Serial
    private static final long serialVersionUID = 2760778574696026630L;
    /**
     * 主键
     */
    private Long id;


    /**
     * 正常/警告/异常/错误
     */
    private String tag;

    /**
     * 标题
     */
    private String title;

    /**
     * 说明备注
     */
    private String content;

    /**
     * 被记录对象ID
     */
    private Long userId;

    private Long adminId;

    private String ipAddress;

    private String ip;
    /**
     * 请求信息
     */
    private String host;
    private String method;
    private String uri;
    /**
     * 系统和浏览器信息
     */
    private String ua;

    public static RecordLog fastBuildAdminLog(Long adminId, String title) {
        RecordLog recordLog = LogUtils.getLog();
        recordLog.setAdminId(adminId);
        recordLog.setTitle(title);
        recordLog.setTag("正常");
        return recordLog;
    }

    public static RecordLog fastBuildAdminLog(Long adminId, String title, String content) {
        RecordLog recordLog = LogUtils.getLog();
        recordLog.setAdminId(adminId);
        recordLog.setTitle(title);
        recordLog.setContent(content);
        recordLog.setTag("正常");
        return recordLog;
    }
}
