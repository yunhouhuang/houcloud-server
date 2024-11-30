/*
 * Copyright (C) 2022 Guang zhou jing ji zhi guang ke ji you xian gong si.
 * All rights reserved.
 * Official Web Site: http://www.lateotu.com.
 */
package com.houcloud.example.common.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.fill.Property;

import java.util.Collections;


/**
 * <p>
 * 代码生成
 * </p>
 *
 * @author <a href="mailto:yunhouhuang@gmail.com">yunhouhuang@gmail.com</a>
 */
public class MyBatisPlusGenerator {

    public static final String URL = "127.0.0.1:3306";
    public static final String USERNAME = "root";
    private static final String PASSWORD = "houcloud";
    private static final String DATABASE = "houcloud_db";

    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://%s/%s?characterEncoding=utf-8&useSSL=false&allowPublicKeyRetrieval=true&serverTimeZone=GMT+8".formatted(URL,DATABASE), USERNAME, PASSWORD)
                .globalConfig(builder -> {
                    builder.author("yunhouhuang@gmail.com") // 设置作
                            .enableSpringdoc() // 使用Spring文档
                            .disableOpenDir() // 关闭生成代码后自动打开目录
                            .outputDir("C:\\Users\\Administrator\\IdeaProjects\\houcloud-server\\src\\main\\java"); // 指定输出目录
                })
                .strategyConfig(builder -> {
                    builder
                            .enableSkipView()
                            .disableSqlFilter() // 禁用 SQL
                            .addInclude(
                                    "t_user"
                            ) // 生成指定表。空则生成全部
                            .addTablePrefix("t_") // 过滤表名
                            .entityBuilder().enableFileOverride()// 实体类开启覆盖
                            .versionColumnName("version")
                            .versionPropertyName("version")
                            .logicDeleteColumnName("deleted_at") // 逻辑删除字段
                            .addTableFills(new Property("updatedAt", FieldFill.INSERT_UPDATE))
                            .addTableFills(new Property("createdAt", FieldFill.INSERT))
                            .enableLombok()
                            .enableActiveRecord()
                            .idType(IdType.AUTO)
                            .controllerBuilder().enableFileOverride().enableRestStyle()
                            // 控制器代码覆盖
                            .build();
                })
                .packageConfig(builder -> builder.parent("com.houcloud")
                        .moduleName("example")
                        .entity("model.entity")
                        .service("service")
                        .serviceImpl("service.impl")
                        .mapper("mapper")
                        .xml("mapperxml")
                        .controller("controller")
                        // 这里如果需要xml需要配置路径
                        .pathInfo(Collections.singletonMap(OutputFile.xml, "src/main/resources/mapperxml")
                        ).build())
                .strategyConfig(builder -> {
                    // 自定义模板
                    builder
                            .entityBuilder().javaTemplate("templates/entity.java")
                            .controllerBuilder().template("templates/controller.java")
                            .build();

                })
                .execute();
    }

}
