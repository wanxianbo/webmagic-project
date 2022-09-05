package com.wanxb.webmagic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wanxianbo
 * @description web-magic 启动类
 * @date 创建于 2022/9/2
 */
@SpringBootApplication(scanBasePackages = {"com.wanxb"})
@MapperScan(value = {"com.wanxb.webmagic.**.com"})
public class WebmagicApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebmagicApplication.class, args);
    }
}
