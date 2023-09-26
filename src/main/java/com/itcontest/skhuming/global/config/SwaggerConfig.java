package com.itcontest.skhuming.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebMvc
public class SwaggerConfig {

    // 배포 후 주소 스웨거 적용
    @Value("${myapp.api-url}")
    private String prodUrl;

    private final Server localServer = new Server("local", "http://localhost:8080", "for local usages", Collections.emptyList(), Collections.emptyList());
    private final Server prodServer = new Server("prod", prodUrl, "for prod", Collections.emptyList(), Collections.emptyList());

    private ApiInfo swaggerInfo() {
        return new ApiInfoBuilder()
                .title("Skhuming API")
                .description("Skhuming API Docs")
                .version("1.0")
                .build();
    }

    @Bean
    public Docket adminApi() {
        return new Docket(DocumentationType.OAS_30)
                .servers(localServer, prodServer)
                .groupName("Admin Api")
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(List.of(accessToken()))
                .apiInfo(this.swaggerInfo())
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.itcontest.skhuming.admin"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false);
    }

    @Bean
    public Docket emailApi() {
        return new Docket(DocumentationType.OAS_30)
                .groupName("Email Api")
                .servers(localServer, prodServer)
                .apiInfo(this.swaggerInfo())
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.itcontest.skhuming.email"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false);
    }

    @Bean
    public Docket mainApi() {
        return new Docket(DocumentationType.OAS_30)
                .groupName("Main Api")
                .servers(localServer, prodServer)
                .apiInfo(this.swaggerInfo())
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.itcontest.skhuming.main"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false);
    }

    @Bean
    public Docket memberApi() {
        return new Docket(DocumentationType.OAS_30)
                .groupName("Member Api")
                .servers(localServer, prodServer)
                .apiInfo(this.swaggerInfo())
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(List.of(accessToken()))
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.itcontest.skhuming.member"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false);
    }

    @Bean
    public Docket mileageApi() {
        return new Docket(DocumentationType.OAS_30)
                .groupName("Mileage Api")
                .servers(localServer, prodServer)
                .apiInfo(this.swaggerInfo())
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(List.of(accessToken()))
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.itcontest.skhuming.mileage"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false);
    }

    @Bean
    public Docket noticeApi() {
        return new Docket(DocumentationType.OAS_30)
                .groupName("Notice Api")
                .servers(localServer, prodServer)
                .apiInfo(this.swaggerInfo())
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(List.of(accessToken()))
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.itcontest.skhuming.notice"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false);
    }

    @Bean
    public Docket rankingApi() {
        return new Docket(DocumentationType.OAS_30)
                .groupName("Ranking Api")
                .servers(localServer, prodServer)
                .apiInfo(this.swaggerInfo())
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(List.of(accessToken()))
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.itcontest.skhuming.ranking"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false);
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return List.of(new SecurityReference("Authorization", authorizationScopes));
    }

    private ApiKey accessToken() {
        return new ApiKey("Authorization", "Bearer", "header");
    }

}
