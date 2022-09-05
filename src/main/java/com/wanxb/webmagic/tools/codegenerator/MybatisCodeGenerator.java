package com.wanxb.webmagic.tools.codegenerator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.builder.Controller;
import com.baomidou.mybatisplus.generator.config.builder.Entity;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Properties;

import static org.apache.commons.lang3.SystemUtils.USER_DIR;

/**
 * @author wanxianbo
 * @description Mybatis-generator代码生成器
 * @date 创建于 2022/9/2
 */
@Slf4j
public class MybatisCodeGenerator {

    private static final String GLOBAL = "global";

    private static final String PACKAGING = "package";

    private static final String STRATEGY = "strategy";

    private static final String XML = "xml";

    private static final String TEMPLATE = "template";

    public static String getSrcDir(String projectDir) {
        return Paths.get(USER_DIR, projectDir, "src/main/java").toString();
    }


    public static FastAutoGenerator buildAutoGenerator(ClassLoader cl, String propertiesFile) {
        // 1.解析code-generator.properties文件
        Properties properties = parseCodeProperties(cl, propertiesFile);
        // 设置数据库连接信息
        FastAutoGenerator generator = FastAutoGenerator.create(properties.getProperty("datasource.url"),
                properties.getProperty("datasource.username"), properties.getProperty("datasource.password"));
        // globalConfig
        generator.globalConfig(builder -> {
            builder.author(getPropertyValue(properties, GLOBAL, "author"))
                    .fileOverride()
                    .outputDir(getSrcDir(getPropertyValue(properties, GLOBAL, "project_dir")));
            if (Boolean.TRUE.equals(Boolean.valueOf(getPropertyValue(properties, GLOBAL, "swagger")))) {
                builder.enableSwagger();
            }
            if (Boolean.TRUE.equals(Boolean.valueOf(getPropertyValue(properties, GLOBAL, "kotlin")))) {
                builder.enableKotlin();
            }
            if (Boolean.FALSE.equals(Boolean.valueOf(getPropertyValue(properties, GLOBAL, "open")))) {
                builder.disableOpenDir();
            }
        });
        // packageConfig
        generator.packageConfig(builder ->
                builder.moduleName(getPropertyValue(properties, PACKAGING, "module_name"))
                        .parent(getPropertyValue(properties, PACKAGING, "parent"))
                        // 设置mapper.xml的生成路径
                        .pathInfo(Collections.singletonMap(OutputFile.xml,
                                Paths.get(USER_DIR, getPropertyValue(properties, XML, "output_dir")).toString())));
        generator.strategyConfig(builder -> {
            builder.addInclude(getPropertyValue(properties, STRATEGY, "includes"));
            // controller
            Controller.Builder controllerBuilder = builder.controllerBuilder();
            String superControllerClass = getPropertyValue(properties, STRATEGY, "super_controller_class");
            if (StringUtils.isNotEmpty(superControllerClass)) {
                controllerBuilder.superClass(superControllerClass);
            }
            if (Boolean.TRUE.equals(Boolean.valueOf(getPropertyValue(properties, STRATEGY, "rest_controller_style")))) {
                controllerBuilder.enableRestStyle();
            }
            if (Boolean.TRUE.equals(Boolean.valueOf(getPropertyValue(properties, STRATEGY, "controller_mapping_hyphen_style")))) {
                controllerBuilder.enableHyphenStyle();
            }
            // entity
            Entity.Builder entityBuilder = builder.entityBuilder();
            String superEntityClass = getPropertyValue(properties, STRATEGY, "super_entity_class");
            String superEntityColumns = getPropertyValue(properties, STRATEGY, "super_entity_columns");
            if (StringUtils.isNotEmpty(superEntityClass)) {
                entityBuilder.superClass(superEntityClass);
            }
            if (StringUtils.isNotEmpty(superEntityColumns)) {
                entityBuilder.addSuperEntityColumns(StringUtils.split(superEntityColumns, ','));
            }
            if (Boolean.TRUE.equals(Boolean.valueOf(getPropertyValue(properties, STRATEGY, "entity_lombok_model")))) {
                entityBuilder.enableLombok();
            }
        });

        // 自定义模板
//        generator.templateConfig(builder -> builder.entity(getPropertyValue(properties, TEMPLATE, "entity"))
//                .mapper(getPropertyValue(properties, TEMPLATE, "mapper"))
//                .xml(getPropertyValue(properties, TEMPLATE, "xml"))
//                .service(getPropertyValue(properties, TEMPLATE, "service"))
//                .serviceImpl(getPropertyValue(properties, TEMPLATE, "serviceImpl_impl"))
//                .controller(getPropertyValue(properties, TEMPLATE, "controller")));
        generator.templateEngine(new FreemarkerTemplateEngine());
        return generator;
    }

    private static Properties parseCodeProperties(ClassLoader cl, String... propertyFiles) {
        Properties properties = new Properties();
        Arrays.stream(propertyFiles).forEach(p -> {
            try {
                properties.load(new InputStreamReader(Objects.requireNonNull(cl.getResourceAsStream(p)), StandardCharsets.UTF_8));
            } catch (IOException e) {
                log.warn("cannot read property file: {}", p);
            }
        });
        return properties;
    }

    private static String getPropertyValue(Properties properties, String prefix, String keyName) {
        return properties.getProperty(prefix + "." + keyName);
    }

    public static void main(String[] args) {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        MybatisCodeGenerator
                .buildAutoGenerator(cl, "code-generator.properties")
                .execute();
    }
}
