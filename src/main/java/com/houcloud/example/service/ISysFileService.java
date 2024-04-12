package com.houcloud.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.houcloud.example.model.entity.SysFile;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 系统文件 服务类
 * </p>
 *
 * @author HouCloud
 * @since 2023-01-21
 */
public interface ISysFileService extends IService<SysFile> {

    SysFile uploadFile(MultipartFile file);
}
