package com.houcloud.example.service.impl;

import com.houcloud.example.model.entity.Issue;
import com.houcloud.example.mapper.IssueMapper;
import com.houcloud.example.service.IIssueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 常见问题 服务实现类
 * </p>
 *
 * @author HouCloud
 * @since 2023-01-25
 */
@Service
public class IssueServiceImpl extends ServiceImpl<IssueMapper, Issue> implements IIssueService {

}
