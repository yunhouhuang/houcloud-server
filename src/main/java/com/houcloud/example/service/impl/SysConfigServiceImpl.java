package com.houcloud.example.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.houcloud.example.mapper.SysConfigMapper;
import com.houcloud.example.model.entity.SysConfig;
import com.houcloud.example.service.ISysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * 系统变量配置 服务实现类
 * </p>
 *
 * @author HouCloud
 * @since 2023-01-21
 */
@Slf4j
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements ISysConfigService {

    @Override
    public String getValueByKey(String key) {
        SysConfig config = getByKey(key);
        if (Objects.isNull(config)){
            return "";
        }
        return config.getValue();
    }

    @Override
    public SysConfig getByKey(String key) {
        return getOne(Wrappers.<SysConfig>lambdaQuery().eq(SysConfig::getKey, key));
    }

    @Override
    public boolean setValueByKey(String key, String value) {
        return update(Wrappers.<SysConfig>lambdaUpdate().eq(SysConfig::getKey, key).set(SysConfig::getValue, value));
    }
}
