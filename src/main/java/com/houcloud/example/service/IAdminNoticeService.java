package com.houcloud.example.service;

import com.houcloud.example.model.entity.AdminNotice;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 管理员消息通知 服务类
 * </p>
 *
 * @author HouCloud
 * @since 2023-01-19
 */
public interface IAdminNoticeService extends IService<AdminNotice> {


    /**
     * 给指定管理员发送通知
     * @param adminId 管理员ID
     * @param title 通知标题
     * @param content 通知内容
     * @param refType 关联类型
     * @param refId 关联ID
     */
    void sendAdminNotice(Long adminId, String title, String content, String refType, Long refId);


    /**
     * 给所有管理员发送通知
     * @param title 通知标题
     * @param content 通知内容
     * @param refType 关联类型
     * @param refId 关联ID
     */
    void sendGlobalMessage(String title, String content, String refType, Long refId);
}
