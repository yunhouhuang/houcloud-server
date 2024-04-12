package com.houcloud.example.service;

import com.houcloud.example.model.entity.SysConfig;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统变量配置 服务类
 * </p>
 *
 * @author HouCloud
 * @since 2023-01-21
 */
public interface ISysConfigService extends IService<SysConfig> {

    /**
     * 根据key获取value
     * @param key key
     * @return String
     */
    String getValueByKey(String key);


    /**
     * 根据key获取配置
     * @param key key
     * @return String
     */
    SysConfig getByKey(String key);


    /**
     * 根据key获取配置
     * @param key key
     * @return String
     */
    boolean setValueByKey(String key,String value);

}
