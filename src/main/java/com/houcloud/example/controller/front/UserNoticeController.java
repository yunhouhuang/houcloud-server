package com.houcloud.example.controller.front;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.houcloud.example.common.result.Result;
import com.houcloud.example.common.security.interceptor.authorize.Authorize;
import com.houcloud.example.common.security.token.store.AuthContext;
import com.houcloud.example.model.entity.UserNotice;
import com.houcloud.example.model.request.IdBody;
import com.houcloud.example.model.request.IdsBody;
import com.houcloud.example.model.request.PageListParams;
import com.houcloud.example.service.IUserNoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static com.houcloud.example.common.constants.Constants.*;


/**
 * <p>
 * 用户消息通知接口
 * </p>
 *
 * @author HouCloud
 * @since 2023-01-24
 */
@Tag(name = "用户消息通知接口")
@RestController
@RequestMapping("/api/front/user/notice")
@Authorize
public class UserNoticeController {

    @Resource
    private IUserNoticeService userNoticeService;

    @Operation(summary = "获取用户消息通知详情")
    @GetMapping
    public Result<UserNotice> getUserNotice(@RequestParam Long id) {
        Long userId = AuthContext.getUserId();
        UserNotice userNotice = userNoticeService.getOne(Wrappers.<UserNotice>lambdaQuery().eq(UserNotice::getUserId, userId).eq(UserNotice::getId, id));
        if (Objects.isNull(userNotice)) {
            return Result.notfound("用户消息通知未找到");
        }
        userNoticeService.lambdaUpdate().set(UserNotice::getStatus,READ_STATUS).eq(UserNotice::getUserId,userId).eq(UserNotice::getId,id).update();
        return Result.success(userNotice);
    }

    @Operation(summary = "获取用户未读消息数")
    @GetMapping("/num")
    public Result<Long> getUnreadNoticeNum() {
        Long userId = AuthContext.getUserId();
        Long count = userNoticeService.count(
                Wrappers.<UserNotice>lambdaQuery()
                        .eq(UserNotice::getUserId, userId).eq(UserNotice::getStatus, UNREAD_STATUS));
        return Result.success(count);
    }

    @Operation(summary = "获取用户消息通知列表")
    @GetMapping("/list")
    public Result<Page<UserNotice>> getUserNoticeList(PageListParams params) {
        Long userId = AuthContext.getUserId();
        LambdaQueryWrapper<UserNotice> wrapper = Wrappers.<UserNotice>lambdaQuery().orderByDesc(UserNotice::getCreatedAt);
        if (StrUtil.isNotBlank(params.getKeywords())) {
            wrapper.like(UserNotice::getTitle, params.getKeywords());
        }
        if (Objects.nonNull(userId)) {
            wrapper.eq(UserNotice::getUserId, userId);
        }
        Page<UserNotice> userNoticePage = userNoticeService.page(Page.of(params.getPage(), params.getLimit()), wrapper);
        return Result.success(userNoticePage);
    }


    @Operation(summary = "批量已读消息")
    @PutMapping("/read")
    public Result<Void> updateUserNotice(@Valid @RequestBody IdsBody<Long> idsBody) {
        Long userId = AuthContext.getUserId();
        boolean update = userNoticeService.update(Wrappers.<UserNotice>lambdaUpdate().in(UserNotice::getId, idsBody.getIds()).eq(UserNotice::getUserId, userId).set(UserNotice::getStatus, READ_STATUS));
        return update ? Result.success() : Result.fail();
    }

    @Operation(summary = "删除用户消息通知")
    @DeleteMapping
    public Result<UserNotice> deleteUserNotice(@Valid @RequestBody IdBody<Long> idBody) {
        Long userId = AuthContext.getUserId();
        UserNotice userNotice = userNoticeService.getOne(Wrappers.<UserNotice>lambdaQuery().eq(UserNotice::getUserId, userId).eq(UserNotice::getId, idBody.getId()));
        if (Objects.isNull(userNotice)) {
            return Result.fail("用户消息通知不存在");
        }
        boolean deleted = userNoticeService.removeById(userNotice);
        return deleted ? Result.success(userNotice) : Result.fail();
    }
}
