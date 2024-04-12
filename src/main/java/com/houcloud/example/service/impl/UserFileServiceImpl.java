package com.houcloud.example.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.houcloud.example.common.exception.BusinessException;
import com.houcloud.example.common.oss.FileDetails;
import com.houcloud.example.common.oss.OssService;
import com.houcloud.example.common.security.token.store.AuthUtil;
import com.houcloud.example.mapper.UserFileMapper;
import com.houcloud.example.model.entity.UserFile;
import com.houcloud.example.service.IUserFileService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 * 用户文件 服务实现类
 * </p>
 *
 * @author HouCloud
 * @since 2023-01-24
 */
@Service
public class UserFileServiceImpl extends ServiceImpl<UserFileMapper, UserFile> implements IUserFileService {
    @Resource
    private OssService ossService;
    @Override
    public UserFile uploadFile(MultipartFile file) {
        Long userId = AuthUtil.getUserId();
        byte[] fileByte;
        String oldFileName = file.getOriginalFilename();
        try {
            fileByte = IoUtil.readBytes(file.getInputStream());
        } catch (IOException e) {
            log.error("用户文件上传异常", e);
            throw BusinessException.exception("上传失败");
        }
        FileDetails fileDetails = ossService.uploadFile(fileByte, oldFileName);
        UserFile userFile = new UserFile();
        String original = file.getOriginalFilename();
        userFile.setUserId(userId);
        userFile.setName(fileDetails.getFileName());
        userFile.setUrl(fileDetails.getUrl());
        userFile.setBucket(fileDetails.getBucket());
        userFile.setName(original);
        userFile.setOldName(oldFileName);
        userFile.setSize(file.getSize());
        userFile.setSuffix(FileUtil.extName(original));
        save(userFile);
        return userFile;
    }
}
