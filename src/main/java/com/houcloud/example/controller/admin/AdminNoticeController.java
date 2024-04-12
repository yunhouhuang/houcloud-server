package com.houcloud.example.controller.admin;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.houcloud.example.common.result.Result;
import com.houcloud.example.common.security.token.store.AuthUtil;
import com.houcloud.example.model.entity.AdminNotice;
import com.houcloud.example.model.request.IdBody;
import com.houcloud.example.model.request.IdsBody;
import com.houcloud.example.model.request.PageListParams;
import com.houcloud.example.service.IAdminNoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static com.houcloud.example.common.constants.Constants.READ_STATUS;
import static com.houcloud.example.common.constants.Constants.UNREAD_STATUS;


/**
 * <p>
 * 管理员消息通知接口
 * </p>
 *
 * @author HouCloud
 * @since 2023-01-19
 */
@Tag(name = "管理员消息通知接口")
@RestController
@RequestMapping("/api/admin/notice")
public class AdminNoticeController {

    @Resource
    private IAdminNoticeService adminNoticeService;

    @Operation(summary = "获取管理员消息通知详情")
    @GetMapping
    public Result<AdminNotice> getAdminNotice(@RequestParam Long id) {
        Long adminId = AuthUtil.getAdminId();
        AdminNotice adminNotice = adminNoticeService.getOne(
                Wrappers.<AdminNotice>
                        lambdaQuery().eq(AdminNotice::getId, id).eq(AdminNotice::getAdminId, adminId));
        if (Objects.isNull(adminNotice)) {
            return Result.noFound("管理员消息通知未找到");
        }
        // 更新状态
        adminNoticeService.update(Wrappers.<AdminNotice>lambdaUpdate().eq(AdminNotice::getId, adminNotice.getId()).eq(AdminNotice::getAdminId, adminId).set(AdminNotice::getStatus, READ_STATUS));
        return Result.success(adminNotice);
    }

    @Operation(summary = "获取管理员未读消息数")
    @GetMapping("/num")
    public Result<Long> getUnreadNoticeNum() {
        Long adminId = AuthUtil.getAdminId();
        Long count = adminNoticeService.count(
                Wrappers.<AdminNotice>lambdaQuery()
                        .eq(AdminNotice::getAdminId, adminId).eq(AdminNotice::getStatus, UNREAD_STATUS));
        return Result.success(count);
    }

    @Operation(summary = "获取管理员消息通知列表")
    @GetMapping("/list")
    public Result<Page<AdminNotice>> getAdminNoticeList(PageListParams params) {
        Long adminId = AuthUtil.getAdminId();
        LambdaQueryWrapper<AdminNotice> wrapper = Wrappers.<AdminNotice>lambdaQuery().eq(AdminNotice::getAdminId, adminId).orderByDesc(AdminNotice::getCreatedAt);
        if (StrUtil.isNotBlank(params.getKeywords())) {
            wrapper.like(AdminNotice::getTitle, params.getKeywords()).or(r -> r
                    .like(AdminNotice::getContent, params.getKeywords()));
        }
        Page<AdminNotice> adminNoticePage = adminNoticeService.page(Page.of(params.getPage(), params.getLimit()), wrapper);
        return Result.success(adminNoticePage);
    }


    @Operation(summary = "标记已读管理员消息通知")
    @PutMapping
    public Result<AdminNotice> batchReadNotice(@RequestBody IdsBody<Long> body) {
        Long adminId = AuthUtil.getAdminId();
        boolean update = adminNoticeService.update(Wrappers.<AdminNotice>lambdaUpdate()
                .eq(AdminNotice::getAdminId, adminId)
                .in(AdminNotice::getId, body.getIds())
                .set(AdminNotice::getStatus, READ_STATUS));
        return update ? Result.success() : Result.fail();
    }

    @Operation(summary = "删除管理员消息通知")
    @DeleteMapping
    public Result<AdminNotice> deleteAdminNotice(@Valid @RequestBody IdBody<Long> idBody) {
        AdminNotice adminNotice = adminNoticeService.getById(idBody.getId());
        if (Objects.isNull(adminNotice)) {
            return Result.fail("管理员消息通知不存在");
        }
        boolean deleted = adminNoticeService.removeById(adminNotice);
        return deleted ? Result.success(adminNotice) : Result.fail();
    }
}
