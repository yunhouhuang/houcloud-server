package com.houcloud.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.houcloud.example.common.constants.Constants;
import com.houcloud.example.mapper.UserNoticeMapper;
import com.houcloud.example.model.entity.UserNotice;
import com.houcloud.example.service.IUserNoticeService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static com.houcloud.example.common.constants.Constants.ONE_STR;

/**
 * <p>
 * 用户消息通知 服务实现类
 * </p>
 *
 * @author HouCloud
 * @since 2023-01-24
 */
@Service
public class UserNoticeServiceImpl extends ServiceImpl<UserNoticeMapper, UserNotice> implements IUserNoticeService {
    @Override
    public void sendUserNotice(Long userId, String title, String content, String refType, Long refId) {
        UserNotice userNotice = new UserNotice();
        userNotice.setUserId(userId);
        userNotice.setStatus(Constants.ZERO_STR);
        userNotice.setContent(content);
        userNotice.setTitle(title);
        userNotice.setRefId(refId);
        userNotice.setRefType(refType);
        save(userNotice);
    }

    @Async
    @Override
    public void sendUserNotice(Long userId, String title, String content) {
        sendUserNotice(userId,title,content,null,null);
    }


    @Override
    public void sendGlobalMessage(String title, String content, String refType, Long refId) {
        UserNotice userNotice = new UserNotice();
        userNotice.setStatus(ONE_STR);
        userNotice.setContent(content);
        userNotice.setTitle(title);
        userNotice.setRefId(refId);
        userNotice.setRefType(refType);
        userNotice.setGlobal(true);
        save(userNotice);
    }

}
