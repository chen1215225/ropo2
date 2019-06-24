package com.delai.bees.tops.web.config;

import com.delai.bees.tops.web.filter.CorsFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;


/**
 * Session 机制 统一配置<br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2018/12/13.
 */
@Slf4j
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Value("${setting.web.cors.enable:false}")
    private Boolean cors;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        if (cors) {
            log.debug("启用跨域支持功能, cors={}", cors);
            registry.addMapping("/**")
                    .allowedOrigins("*")
                    .allowedMethods("*")
                    .allowedHeaders("*")
                    .allowCredentials(true);
        }
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //将所有/static/** 访问都映射到classpath:/static/ 目录下
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/views/**").addResourceLocations("classpath:/views/");
//        registry.addResourceHandler("/**/*.js*").addResourceLocations("classpath:/static/");
//        registry.addResourceHandler("/**/*.css*").addResourceLocations("classpath:/static/");
//        registry.addResourceHandler("/**/*.woff").addResourceLocations("classpath:/static/");
//        registry.addResourceHandler("/**/*.ttf").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/static/");
    }
}
