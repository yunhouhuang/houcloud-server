package com.houcloud.example.controller.admin;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.houcloud.example.common.logger.annotation.Logger;
import com.houcloud.example.common.result.Result;
import com.houcloud.example.common.security.permission.annotation.Permission;
import com.houcloud.example.model.entity.Feedback;
import com.houcloud.example.model.request.IdBody;
import com.houcloud.example.model.request.PageListParams;
import com.houcloud.example.service.IFeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


/**
 * <p>
 * 意见反馈接口
 * </p>
 *
 * @author HouCloud
 * @since 2023-01-25
 */
@Tag(name = "意见反馈接口")
@RestController
@RequestMapping("/api/admin/feedback")
public class FeedbackManageController {

        @Resource
        private IFeedbackService feedbackService;

        @Operation(summary = "获取意见反馈详情")
        @GetMapping
        public Result<Feedback> getFeedback(@RequestParam Long id){
                Feedback feedback = feedbackService.getById(id);
                if (Objects.isNull(feedback)){
                    return Result.noFound("意见反馈未找到");
                }
                return Result.success(feedback);
        }


        @Operation(summary = "获取意见反馈列表")
        @GetMapping("/list")
        public Result<Page<Feedback>> getFeedbackList(PageListParams params){
                LambdaQueryWrapper<Feedback> wrapper = Wrappers.<Feedback>lambdaQuery().orderByDesc(Feedback::getCreatedAt);
                if (StrUtil.isNotBlank(params.getKeywords())){
                        // todo
                }
                Page<Feedback> feedbackPage = feedbackService.page(Page.of(params.getPage(), params.getLimit()), wrapper);
                return Result.success(feedbackPage);
        }


        @Operation(summary = "更新意见反馈")
        @PutMapping
        @Logger("更新意见反馈")
        @Permission("feedback:update")
        public Result<Feedback> updateFeedback(@RequestBody Feedback feedback){
                boolean update = feedbackService.updateById(feedback);
                return update?Result.success(feedback):Result.fail();
        }

        @Operation(summary = "删除意见反馈")
        @DeleteMapping
        @Permission("feedback:delete")
        public Result<Feedback> deleteFeedback(@Valid @RequestBody IdBody<Long> idBody){
                Feedback feedback = feedbackService.getById(idBody.getId());
                if (Objects.isNull(feedback)){
                return Result.fail("意见反馈不存在");
                }
                boolean deleted = feedbackService.removeById(feedback);
                return deleted?Result.success(feedback):Result.fail();
        }
}
