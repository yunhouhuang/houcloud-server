package com.houcloud.example.service.impl;

import com.houcloud.example.model.entity.Category;
import com.houcloud.example.mapper.CategoryMapper;
import com.houcloud.example.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 分类表 服务实现类
 * </p>
 *
 * @author HouCloud
 * @since 2023-01-21
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

}
