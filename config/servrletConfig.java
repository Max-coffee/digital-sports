package com.example.spring_boot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "server")
public class servrletConfig {
    private String address;
    private int port;
}
