package com.delai.bees.tops.dao.config;

import com.github.miemiedev.mybatis.paginator.OffsetLimitInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2018/8/5.
 */
@Configuration
public class SqlSessionPluginsConfig {

    @Value("${setting.mybatis.paginator.dialect:com.github.miemiedev.mybatis.paginator.dialect.MySQLDialect}")
    private String dialect;

    @Bean
    public Interceptor getInterceptor(){
        OffsetLimitInterceptor interceptor = new OffsetLimitInterceptor();
        interceptor.setDialectClass(dialect);
        return interceptor;
    }
}
