package com.example.spring_boot;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import com.example.spring_boot.config.servrletConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableConfigurationProperties(servrletConfig.class)
@EnableCreateCacheAnnotation
@EnableMethodCache(basePackages = "com.example.spring_boot")
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Application.class, args);
        servrletConfig bean = run.getBean(servrletConfig.class);
        System.out.println(bean);

    }

}
