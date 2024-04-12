package com.houcloud.example.service.impl;

import com.houcloud.example.model.entity.Feedback;
import com.houcloud.example.mapper.FeedbackMapper;
import com.houcloud.example.service.IFeedbackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 意见反馈 服务实现类
 * </p>
 *
 * @author HouCloud
 * @since 2023-01-25
 */
@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback> implements IFeedbackService {

}
