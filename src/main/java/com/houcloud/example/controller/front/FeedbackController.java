package com.houcloud.example.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.houcloud.example.common.result.Result;
import com.houcloud.example.common.security.interceptor.authorize.Authorize;
import com.houcloud.example.common.security.token.store.AuthUtil;
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
@RequestMapping("/api/front/feedback")
@Authorize
public class FeedbackController {
        @Resource
        private IFeedbackService feedbackService;

        @Operation(summary = "获取意见反馈详情")
        @GetMapping
        public Result<Feedback> getFeedback(@RequestParam Long id){
                Long userId = AuthUtil.getUserId();
                Feedback feedback = feedbackService.getOne(Wrappers.<Feedback>lambdaQuery().eq(Feedback::getUserId, userId).eq(Feedback::getId, id));
                if (Objects.isNull(feedback)){
                    return Result.noFound("意见反馈未找到");
                }
                return Result.success(feedback);
        }


        @Operation(summary = "获取意见反馈列表")
        @GetMapping("/list")
        public Result<Page<Feedback>> getFeedbackList(PageListParams params){
                Long userId = AuthUtil.getUserId();
                LambdaQueryWrapper<Feedback> wrapper = Wrappers.<Feedback>lambdaQuery().orderByDesc(Feedback::getCreatedAt);
                wrapper.eq(Feedback::getUserId,userId);
                Page<Feedback> feedbackPage = feedbackService.page(Page.of(params.getPage(), params.getLimit()), wrapper);
                return Result.success(feedbackPage);
        }


        @Operation(summary = "添加意见反馈")
        @PostMapping
        public Result<Feedback> addFeedback(@RequestBody Feedback feedback){
                Long userId = AuthUtil.getUserId();
                feedback.setUserId(userId);
                boolean save = feedbackService.save(feedback);
                return save?Result.success(feedback):Result.fail();
        }


        @Operation(summary = "删除意见反馈")
        @DeleteMapping
        public Result<Feedback> deleteFeedback(@Valid @RequestBody IdBody<Long> idBody){
                Long userId = AuthUtil.getUserId();
                Feedback feedback = feedbackService.getOne(Wrappers.<Feedback>lambdaQuery().eq(Feedback::getUserId, userId).eq(Feedback::getId, idBody.getId()));
                if (Objects.isNull(feedback)){
                return Result.fail("意见反馈不存在");
                }
                boolean deleted = feedbackService.removeById(feedback);
                return deleted?Result.success(feedback):Result.fail();
        }
}
