<center>Springboot 整合 Swagger</center>



#### 环境版本

| 服务名称    | 版本号    |
| ----------- | --------- |
| Spring boot | 2.7.8(GA) |
| Java        | 1.8       |

#### 导入依赖

方式一：

```xml
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-boot-starter</artifactId>
    <version>3.0.0</version>
</dependency>

<dependency>
    <groupId>com.github.xiaoymin</groupId>
    <artifactId>knife4j-spring-ui</artifactId>
    <version>3.0.3</version>
</dependency>
```

方式二：

```xml
<dependency>
  <groupId>com.github.xiaoymin</groupId>
  <artifactId>knife4j-spring-boot-starter</artifactId>
  <version>3.0.3</version>
</dependency>
```

#### 创建配置文件

```java
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
                .globalResponses(HttpMethod.PUT, getGlobalResponseMessage())
                .enable(true);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("swagger")
                .description("swagger接口文档")
                .termsOfServiceUrl("/")
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

        Arrays.stream(ResponseStatus.values()).forEach(s -> {
            responseList.add(new ResponseBuilder()
                    .code(String.valueOf(s.getCode()))
                    .description(s.getMessage())
                    .build());
        });

        return responseList;
    }
}
```

##### 开启身份认证(采用方式二)

```xml
knife4j:
  enable: true
  production: false
  basic:
    enable: true
    username: smile
    password: root
```

##### 异常一：

```java
org.springframework.context.ApplicationContextException: Failed to start bean 'documentationPluginsBootstrapper'; nested exception is java.lang.NullPointerException
```

因为Springfox 使用的路径匹配是基于[AntPathMatcher](https://so.csdn.net/so/search?q=AntPathMatcher&spm=1001.2101.3001.7020)的，而Spring Boot 2.6.X以后使用的是PathPatternMatcher

application.yml 或applicaiton.properties 中添加如下配置

```yml
spring:
  mvc:
    pathmatch:
      matching-strategy: ant_path_matcher
```