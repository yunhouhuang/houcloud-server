package com.houcloud.example.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.houcloud.example.common.result.Result;
import com.houcloud.example.model.entity.Issue;
import com.houcloud.example.model.request.PageListParams;
import com.houcloud.example.service.IIssueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;


/**
 * <p>
 * 常见问题接口
 * </p>
 *
 * @author HouCloud
 * @since 2023-01-25
 */
@Tag(name = "常见问题接口")
@RestController
@RequestMapping("/api/front/issue")
public class IssueController {

        @Resource
        private IIssueService issueService;

        @Operation(summary = "获取常见问题详情")
        @GetMapping
        public Result<Issue> getIssue(@RequestParam Long id){
                Issue issue = issueService.getById(id);
                if (Objects.isNull(issue)){
                    return Result.noFound("常见问题未找到");
                }
                return Result.success(issue);
        }


        @Operation(summary = "获取常见问题列表")
        @GetMapping("/list")
        public Result<Page<Issue>> getIssueList(PageListParams params){
                LambdaQueryWrapper<Issue> wrapper = Wrappers.<Issue>lambdaQuery().orderByDesc(Issue::getCreatedAt);
                Page<Issue> issuePage = issueService.page(Page.of(params.getPage(), params.getLimit()), wrapper);
                return Result.success(issuePage);
        }

}
