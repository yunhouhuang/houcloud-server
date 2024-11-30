package com.houcloud.example.controller.admin;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.houcloud.example.common.logger.annotation.Logger;
import com.houcloud.example.common.result.Result;
import com.houcloud.example.common.security.permission.annotation.Permission;
import com.houcloud.example.model.entity.Issue;
import com.houcloud.example.model.request.IdBody;
import com.houcloud.example.model.request.PageListParams;
import com.houcloud.example.service.IIssueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/api/admin/issue")
public class IssueManageController {

        @Resource
        private IIssueService issueService;

        @Operation(summary = "获取常见问题详情")
        @GetMapping
        public Result<Issue> getIssue(@RequestParam Long id){
                Issue issue = issueService.getById(id);
                if (Objects.isNull(issue)){
                    return Result.notfound("常见问题未找到");
                }
                return Result.success(issue);
        }


        @Operation(summary = "获取常见问题列表")
        @GetMapping("/list")
        public Result<Page<Issue>> getIssueList(PageListParams params){
                LambdaQueryWrapper<Issue> wrapper = Wrappers.<Issue>lambdaQuery().orderByDesc(Issue::getCreatedAt);
                if (StrUtil.isNotBlank(params.getKeywords())){
                        // todo
                }
                Page<Issue> issuePage = issueService.page(Page.of(params.getPage(), params.getLimit()), wrapper);
                return Result.success(issuePage);
        }


        @Operation(summary = "添加常见问题")
        @Logger("添加常见问题")
        @PostMapping
        @Permission("issue:add")
        public Result<Issue> addIssue(@RequestBody Issue issue){
                boolean save = issueService.save(issue);
                return save?Result.success(issue):Result.fail();
        }

        @Operation(summary = "更新常见问题")
        @Logger("更新常见问题")
        @PutMapping
        @Permission("issue:update")
        public Result<Issue> updateIssue(@RequestBody Issue issue){
                boolean update = issueService.updateById(issue);
                return update?Result.success(issue):Result.fail();
        }

        @Operation(summary = "删除常见问题")
        @DeleteMapping
        @Logger("删除常见问题")
        @Permission("issue:delete")
        public Result<Issue> deleteIssue(@Valid @RequestBody IdBody<Long> idBody){
                Issue issue = issueService.getById(idBody.getId());
                if (Objects.isNull(issue)){
                return Result.fail("常见问题不存在");
                }
                boolean deleted = issueService.removeById(issue);
                return deleted?Result.success(issue):Result.fail();
        }
}
