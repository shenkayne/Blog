package com.scr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.scr.controller"))
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("小小西瓜", "https://blog.csdn.net/m0_66908465?spm=1000.2115.3001.5343", "2073609597@qq.com");
        return new ApiInfoBuilder()
                .title("博客文档")
                .description("后端技术栈SpringBoot,SpringSecurity,Mybatis-plus,EasyExcel,Redis,Swagger2")
                .contact(contact)   // 联系方式
                .version("1.1.1")  // 版本
                .build();
    }
}