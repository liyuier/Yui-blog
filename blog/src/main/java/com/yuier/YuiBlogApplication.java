package com.yuier;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author: Yui
 * @Description: 前台代码启动类
 * @DateTime: 2023/7/30 12:21
 **/

// 启动类
@SpringBootApplication
@MapperScan("/com.yuier.mapper")
@EnableScheduling
@EnableSwagger2
public class YuiBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(YuiBlogApplication.class, args);
    }
}
