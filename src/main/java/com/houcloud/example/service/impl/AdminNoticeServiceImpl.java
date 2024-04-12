package com.houcloud.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.houcloud.example.common.constants.Constants;
import com.houcloud.example.mapper.AdminNoticeMapper;
import com.houcloud.example.model.entity.AdminNotice;
import com.houcloud.example.service.IAdminNoticeService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员消息通知 服务实现类
 * </p>
 *
 * @author HouCloud
 * @since 2023-01-19
 */
@Service
public class AdminNoticeServiceImpl extends ServiceImpl<AdminNoticeMapper, AdminNotice> implements IAdminNoticeService {

    @Async
    @Override
    public void sendAdminNotice(Long adminId, String title, String content, String refType, Long refId) {
        AdminNotice adminNotice = new AdminNotice();
        adminNotice.setAdminId(adminId);
        adminNotice.setStatus(Constants.ZERO_STR);
        adminNotice.setContent(content);
        adminNotice.setTitle(title);
        adminNotice.setRefId(refId);
        adminNotice.setRefType(refType);
        save(adminNotice);
    }

    @Async
    @Override
    public void sendGlobalMessage(String title, String content, String refType, Long refId) {
        AdminNotice adminNotice = new AdminNotice();
        adminNotice.setStatus(Constants.ONE_STR);
        adminNotice.setContent(content);
        adminNotice.setTitle(title);
        adminNotice.setRefId(refId);
        adminNotice.setRefType(refType);
        adminNotice.setGlobal(true);
        save(adminNotice);
    }
}
