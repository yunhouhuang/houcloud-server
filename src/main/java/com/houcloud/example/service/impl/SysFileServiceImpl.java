package com.houcloud.example.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.houcloud.example.common.exception.BusinessException;
import com.houcloud.example.common.oss.FileDetails;
import com.houcloud.example.common.oss.OssService;
import com.houcloud.example.common.security.token.store.AuthContext;
import com.houcloud.example.mapper.SysFileMapper;
import com.houcloud.example.model.entity.SysFile;
import com.houcloud.example.service.ISysFileService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 * 系统文件 服务实现类
 * </p>
 *
 * @author HouCloud
 * @since 2023-01-21
 */
@Service
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFile> implements ISysFileService {

    @Resource
    private OssService ossService;
    @Override
    public SysFile uploadFile(MultipartFile file) {
        Long userId = AuthContext.getAdminId();
        byte[] fileByte;
        String oldFileName = file.getOriginalFilename();
        try {
            fileByte = IoUtil.readBytes(file.getInputStream());
        } catch (IOException e) {
            log.error("文件上传异常", e);
            throw BusinessException.exception("上传失败");
        }
        FileDetails fileDetails = ossService.uploadFile(fileByte, oldFileName);
        SysFile systemFile = new SysFile();
        String original = file.getOriginalFilename();
        systemFile.setAdminId(userId);
        systemFile.setOldName(oldFileName);
        systemFile.setName(fileDetails.getFileName());
        systemFile.setUrl(fileDetails.getUrl());
        systemFile.setBucket(fileDetails.getBucket());
        systemFile.setName(original);
        systemFile.setSize(file.getSize());
        systemFile.setSuffix(FileUtil.extName(original));
        save(systemFile);
        return systemFile;
    }
}
