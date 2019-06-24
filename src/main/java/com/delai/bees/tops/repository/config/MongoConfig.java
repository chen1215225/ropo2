package com.delai.bees.tops.repository.config;

import com.mongodb.*;
import lombok.extern.slf4j.Slf4j;
import org.bson.UuidRepresentation;
import org.bson.codecs.UuidCodec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import static org.bson.codecs.configuration.CodecRegistries.fromCodecs;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2018/9/30.
 */
@Slf4j
@Configuration
public class MongoConfig extends AbstractMongoConfiguration {

    @Value("${spring.data.mongodb.host}")
    private String host;

    @Value("${spring.data.mongodb.port}")
    private int port;

    @Value("${spring.data.mongodb.database}")
    private String database;

    @Value("${spring.data.mongodb.username:}")
    private String username;

    @Value("${spring.data.mongodb.password:}")
    private String password;

    @Value("${spring.data.mongodb.authentication-database:}")
    private String authentication;

    @Override
    public MongoClient mongoClient() {
        log.info("初始化MongoConfig：host={}, port={}, database={}, username={}, password={}", host, port, database, username, password);
        ServerAddress server = new ServerAddress(host, port);
        // 设置 MongoDB UUID binary 的解析格式
        MongoClientOptions.Builder builder = MongoClientOptions.builder();
        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(
                CodecRegistries.fromCodecs(new UuidCodec(UuidRepresentation.C_SHARP_LEGACY)),
                MongoClient.getDefaultCodecRegistry());
        builder.codecRegistry(codecRegistry).build();
        MongoClientOptions options = builder.build();

        MongoCredential credential = null;

        if (authentication != null && !authentication.equals("")) {
            credential = MongoCredential.createScramSha1Credential(username, authentication, password.toCharArray());
        } else {
            if (username !=null && !username.equals("")) {
                credential = MongoCredential.createCredential(username, database, password.toCharArray());
            } else {
                // 无密码连接方式
                log.info("初始化MongoConfig: 无密码连接");
                return new MongoClient(server, options);
            }
        }

        return new MongoClient(server, credential, options);
    }

    @Override
    protected String getDatabaseName() {
        return database;
    }


    @Override
    public @Bean
    MongoDbFactory mongoDbFactory() {
        return new SimpleMongoDbFactory(mongoClient(), getDatabaseName());
    }

    @Override
    public @Bean
    MongoTemplate mongoTemplate() throws Exception {
        //remove _class
        MappingMongoConverter converter =
                new MappingMongoConverter(mongoDbFactory(), new MongoMappingContext());
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory(), converter);
        return mongoTemplate;
    }
}
