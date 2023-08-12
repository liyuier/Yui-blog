package com.yuier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;

/**
 * @Author: Yui
 * @Description: TODO
 * @DateTime: 2023/8/10 20:19
 **/

@SpringBootApplication
@MapperScan("com.yuier.mapper")
public class YuiBlogAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(YuiBlogAdminApplication.class, args);
    }
}
