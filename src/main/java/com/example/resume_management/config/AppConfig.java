package com.example.resume_management.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public MyJDBC myJDBC_Connect() {
        return new MyJDBC();
    }
}
