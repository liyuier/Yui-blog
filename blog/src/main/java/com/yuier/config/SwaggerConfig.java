package com.yuier.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @Author: Yui
 * @Description: TODO
 * @DateTime: 2023/8/10 18:13
 **/

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yuier.controller"))
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("Yui", "http://www.liyuri.com", "yuier@yuier.com");
        return new ApiInfoBuilder()
                .title("前台模块接口文档")
                .description("所有前台模块接口的（并不）详细说明")
                .contact(contact)   // 联系方式
                .version("1.1.0")  // 版本
                .build();
    }
}
