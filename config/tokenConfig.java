package com.example.spring_boot.config;

import com.example.spring_boot.interceptor.loginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class tokenConfig implements WebMvcConfigurer {


    @Bean
    public HandlerInterceptor getTokenInterceptor(){
        return new loginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getTokenInterceptor())
        .addPathPatterns("/**")
                .excludePathPatterns("/save")
                .excludePathPatterns("/loginFail")
                .excludePathPatterns("/error")
                .excludePathPatterns("/index.html")
                .excludePathPatterns("/main.html")
                .excludePathPatterns("/web/**");
    }
}
