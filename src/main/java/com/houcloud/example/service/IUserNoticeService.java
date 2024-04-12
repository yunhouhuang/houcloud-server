package com.houcloud.example.service;

import com.houcloud.example.model.entity.UserNotice;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户消息通知 服务类
 * </p>
 *
 * @author HouCloud
 * @since 2023-01-24
 */
public interface IUserNoticeService extends IService<UserNotice> {

    /**
     * 给指定用户发送通知
     * @param userId 用户ID
     * @param title 通知标题
     * @param content 通知内容
     * @param refType 关联类型
     * @param refId 关联ID
     */
    void sendUserNotice(Long userId, String title, String content, String refType, Long refId);

    void sendUserNotice(Long userId, String title, String content);

    /**
     * 给所有用户发送通知
     * @param title 通知标题
     * @param content 通知内容
     * @param refType 关联类型
     * @param refId 关联ID
     */
    void sendGlobalMessage(String title, String content, String refType, Long refId);
}
