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
 *    代码生成
 * </p>
 * @author <a href="mailto:yunhouhuang@gmail.com">yunhouhuang@gmail.com</a>
 */
public class MyBatisPlusGenerator {


    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://127.0.0.1:3306/houcloud_db?characterEncoding=utf-8&useSSL=false&serverTimeZone=GMT+8", "root", "root")
                .globalConfig(builder -> {
                    builder.author("HOUCLOUD")
                            .enableSpringdoc() // 使用Spring文档
                            .disableOpenDir() // 关闭生成代码后自动打开目录
                            .outputDir("C:\\Users\\Administrator\\IdeaProjects\\houcloud-server\\src\\main\\java"); // 指定输出目录
                })
                .strategyConfig(builder -> {
                    builder
                            .enableSkipView()
                            .disableSqlFilter() // 禁用 SQL
                            // 要生成的表
                            .addInclude(
                                    "t_user",
                                    "t_user_log",
                                    "t_user_notice"
                            ) // 生成指定表。空则生成全部
                            .addTablePrefix("t_") // 过滤表名
                            .entityBuilder().enableFileOverride()// 实体类开启覆盖
                            .versionColumnName("version")
                            .versionPropertyName("version")
                            .logicDeleteColumnName("deleted_at") // 逻辑删除字段
                            .addTableFills(new Property("updatedAt", FieldFill.INSERT_UPDATE))
                            .addTableFills(new Property("createdAt", FieldFill.INSERT))
                            .addTableFills(new Property("name"))
                            .enableLombok()
                            .enableActiveRecord()
                            .idType(IdType.AUTO)
                            .controllerBuilder().enableFileOverride().enableRestStyle() // 控制器代码覆盖
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
                        .pathInfo(Collections.singletonMap(OutputFile.xml,"C:\\Users\\Administrator\\IdeaProjects\\houcloud-server\\src\\main\\resources\\mapperxml")
                        ).build())
                .templateConfig(builder -> {
                    // 自定义模板
                    builder
//                            .disable(TemplateType.ENTITY)
                            .entity("templates/entity.java")
//                            .service("templates\\service.java")
//                            .serviceImpl("templates\\serviceImpl.java")
//                            .mapper("templates\\mapper.java")
//                            .xml("templates\\mapper.xml")
                            .controller("templates/controller.java")
                            .build();

                })
                .execute();
    }

}
