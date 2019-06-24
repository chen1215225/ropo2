package com.delai.bees.tops.web.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

/**
 *
 * @author ryan
 *
 * Created by ryan on 上午11:11.
 */
@Slf4j
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${setting.swagger.enable:false}")
    private Boolean enable;

    @Value("${setting.swagger.host:}")
    private String host;

    @Bean
    public Docket api() {
        if (enable) {
            // 激活Swagger文档
            Docket iDocket = new Docket(DocumentationType.SWAGGER_2)
                    .apiInfo(apiInfo())
                    .select()
                    .apis(RequestHandlerSelectors.any())
                    .paths(PathSelectors.any())
                    .build();
            if (!host.equals("")) {
                iDocket.host(host);
            }
            return iDocket;
        } else {
            // 关闭Swagger文档
            return new Docket(DocumentationType.SWAGGER_2)
                    .apiInfo(apiInfo())
                    .select()
                    .apis(RequestHandlerSelectors.none())
                    .paths(PathSelectors.none())
                    .build();
        }
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Top 200 接口")
                .description("Restful 风格接口")
                .version("1.0.3")
                .build();
    }


}

