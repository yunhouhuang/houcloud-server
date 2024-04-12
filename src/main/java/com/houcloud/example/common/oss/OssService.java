package com.houcloud.example.common.oss;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.amazonaws.services.s3.model.S3Object;
import com.houcloud.example.common.exception.BusinessException;
import com.houcloud.example.common.oss.service.OssTemplate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 七牛云接口
 * </p>
 *
 * @author Houcloud@foxmail.com
 * @since 2021/2/23
 */
@Slf4j
@AllArgsConstructor
@Service
public class OssService {

    private final OssProperties ossProperties;

    private final OssTemplate aliOssTemplate;

    /**
     * 上传文件
     *
     * @param file 文件
     * @return 文件详情
     */
    public FileDetails uploadFile(byte[] file, String originalFilename) {
        String fileName = IdUtil.simpleUUID() + StrUtil.DOT + FileUtil.extName(originalFilename);
        String url = fileName;
        if (StrUtil.isNotBlank(ossProperties.getDomain())) {
            url = String.format(ossProperties.getDomain() + "/%s", fileName);
        }
        if (ossProperties.getIsPrivate()) {
            String accessSign = aliOssTemplate.getObjectURL(ossProperties.getBucketName(), fileName, 1);
            log.info("获取Url{}", accessSign);
            url = accessSign;
        }
        FileDetails fileDetails = new FileDetails(fileName, ossProperties.getBucketName(), url);
        try {
            aliOssTemplate.putObject(ossProperties.getBucketName(), fileName, IoUtil.toStream(file));
        } catch (Exception e) {
            log.error("上传失败", e);
            throw BusinessException.exception("上传失败");
        }
        return fileDetails;
    }

    /**
     * 读取文件
     *
     * @param bucket   桶名称
     * @param fileName 文件名
     * @return byte
     */
    public byte[] getFile(String bucket, String fileName) {
        try (S3Object s3Object = aliOssTemplate.getObject(bucket, fileName)) {
            return IoUtil.readBytes(s3Object.getObjectContent());
        } catch (Exception e) {
            log.error("文件读取异常: {}", e.getLocalizedMessage());
        }
        return new byte[0];
    }

    /**
     * 删除文件
     *
     * @param fileName 文件名
     * @return 是否成功
     */
    public Boolean deleteFile(String fileName) {
        try {
            aliOssTemplate.removeObject(ossProperties.getBucketName(), fileName);
        } catch (Exception e) {
            log.error("删除文件失败", e);
            throw BusinessException.exception("删除文件失败");
        }
        return true;
    }

    public String getSign(String fileName) {
        return aliOssTemplate.getObjectURL(ossProperties.getBucketName(), fileName, 1);
    }

    private String getUrl(String path) {
        String ts = "/";
        String substring1 = path.substring(0, 1);
        if (ts.equals(substring1)) {
            path = path.substring(1);
        }
        String substring2 = path.substring(path.length() - 1);
        if (ts.equals(substring2)) {
            path = path.substring(0, path.length() - 2);
        }
        return path;
    }

}
