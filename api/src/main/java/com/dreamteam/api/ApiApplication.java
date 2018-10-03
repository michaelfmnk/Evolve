package com.dreamteam.api;

import com.dreamteam.api.properties.AuthProperties;
import com.dreamteam.api.properties.MailjetProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({AuthProperties.class, MailjetProperties.class})
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }
}
