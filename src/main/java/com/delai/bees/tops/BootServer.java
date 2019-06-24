package com.delai.bees.tops;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2019/1/10.
 */
@SpringBootApplication
@ComponentScan("com.delai.bees.tops")
@EnableAutoConfiguration
@MapperScan("com.delai.bees.tops.dao")
public class BootServer {

    /**
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(BootServer.class, args);
    }

}
