package com.example.spring_boot.config;

import com.example.spring_boot.interceptor.checkUserInterceptor;
import com.example.spring_boot.interceptor.loginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class checkUserConfig implements WebMvcConfigurer {

    @Bean
    public HandlerInterceptor getCheckUserInterceptor(){
        return new checkUserInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getCheckUserInterceptor()).addPathPatterns("/query");

    }
}
