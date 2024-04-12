package com.houcloud.example.controller.front;

import com.houcloud.example.common.result.Result;
import com.houcloud.example.common.security.interceptor.authorize.Authorize;
import com.houcloud.example.model.entity.UserFile;
import com.houcloud.example.service.IUserFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


/**
 * <p>
 * 用户文件接口
 * </p>
 *
 * @author HouCloud
 * @since 2023-01-24
 */
@Tag(name = "用户文件接口")
@RestController
@RequestMapping("/api/front/file")
@Authorize
public class UserFileController {

    @Resource
    private IUserFileService userFileService;

    @Operation(summary = "上传文件")
    @PostMapping
    public Result<UserFile> upload(@RequestParam("file") MultipartFile file) {
        return Result.success("上传成功", userFileService.uploadFile(file));
    }

}
