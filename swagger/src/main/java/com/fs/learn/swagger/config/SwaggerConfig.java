package com.fs.learn.swagger.config;

import com.fs.learn.swagger.utils.response.ResponseStatus;
import org.springframework.http.HttpMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: smile
 * @title: swagger 配置
 * @projectName: learn
 * @description: swagger 配置
 * @date: 2023/2/22 9:57 AM
 */
@Configuration
@EnableOpenApi
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.fs.learn.swagger.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalResponses(HttpMethod.GET, getGlobalResponseMessage())
                .globalResponses(HttpMethod.POST, getGlobalResponseMessage())
                .globalResponses(HttpMethod.DELETE, getGlobalResponseMessage())
                .globalResponses(HttpMethod.PUT, getGlobalResponseMessage());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("swagger")
                .description("swagger接口文档")
                .termsOfServiceUrl("www.fs.com")
                .contact(new Contact("smile", "www.github.com", "ywjmylove@163.com"))
                .version("1.0.0")
                .build();
    }

    /**
    * 生成通用的响应
    * @description 生成通用的响应
    * @author smile
    * @date 2023/2/22
    * @return java.util.List<Response>
    **/
    private List<Response> getGlobalResponseMessage() {
        List<Response> responseList = new ArrayList<>();

        Arrays.stream(ResponseStatus.values()).forEach(s -> responseList.add(new ResponseBuilder()
                .code(String.valueOf(s.getCode()))
                .description(s.getMessage())
                .build()));

        return responseList;
    }
}