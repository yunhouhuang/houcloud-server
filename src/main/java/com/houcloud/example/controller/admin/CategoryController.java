package com.houcloud.example.controller.admin;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.houcloud.example.common.logger.annotation.Logger;
import com.houcloud.example.common.result.Result;
import com.houcloud.example.common.security.permission.annotation.Permission;
import com.houcloud.example.model.entity.Category;
import com.houcloud.example.model.request.IdBody;
import com.houcloud.example.model.request.PageListParams;
import com.houcloud.example.service.ICategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


/**
 * <p>
 * 分类接口
 * </p>
 *
 * @author HouCloud
 * @since 2023-01-22
 */
@Tag(name = "分类接口")
@RestController
@RequestMapping("/api/admin/category")
public class CategoryController {

    @Resource
    private ICategoryService categoryService;

    @Operation(summary = "获取分类详情")
    @GetMapping
    public Result<Category> getCategory(@RequestParam Long id) {
        Category category = categoryService.getById(id);
        if (Objects.isNull(category)) {
            return Result.notfound("分类未找到");
        }
        return Result.success(category);
    }


    @Operation(summary = "获取分类列表")
    @GetMapping("/list")
    public Result<Page<Category>> getCategoryList(PageListParams params) {
        LambdaQueryWrapper<Category> wrapper = Wrappers.<Category>lambdaQuery().orderByDesc(Category::getCreatedAt);
        if (StrUtil.isNotBlank(params.getKeywords())) {
            // todo
        }
        Page<Category> categoryPage = categoryService.page(Page.of(params.getPage(), params.getLimit()), wrapper);
        return Result.success(categoryPage);
    }


    @Operation(summary = "添加分类")
    @PostMapping
    @Logger("添加分类")
    @Permission("category:add")
    public Result<Category> addCategory(@RequestBody Category category) {
        boolean save = categoryService.save(category);
        return save ? Result.success(category) : Result.fail();
    }

    @Operation(summary = "更新分类")
    @PutMapping
    @Logger("更新分类")
    @Permission("category:update")
    public Result<Category> updateCategory(@RequestBody Category category) {
        boolean save = categoryService.updateById(category);
        return save ? Result.success(category) : Result.fail();
    }

    @Operation(summary = "删除分类")
    @DeleteMapping
    @Logger("删除分类")
    @Permission("category:delete")
    public Result<Category> deleteCategory(@Valid @RequestBody IdBody<Long> idBody) {
        Category category = categoryService.getById(idBody.getId());
        if (Objects.isNull(category)) {
            return Result.fail("分类不存在");
        }
        boolean deleted = categoryService.removeById(category);
        return deleted ? Result.success(category) : Result.fail();
    }
}
