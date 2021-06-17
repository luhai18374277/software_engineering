package com.example.tinymall;

import com.example.tinymall.config.ApplicationHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class TinyMallApplication {
    public static void main(String[] args) {
        SpringApplication.run(TinyMallApplication.class, args);
    }
}
