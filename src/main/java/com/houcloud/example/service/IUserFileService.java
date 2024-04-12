package com.houcloud.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.houcloud.example.model.entity.UserFile;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 用户文件 服务类
 * </p>
 *
 * @author HouCloud
 * @since 2023-01-24
 */
public interface IUserFileService extends IService<UserFile> {

    UserFile uploadFile(MultipartFile file);
}
