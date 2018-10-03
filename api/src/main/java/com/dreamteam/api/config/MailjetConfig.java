package com.dreamteam.api.config;

import com.dreamteam.api.properties.MailjetProperties;
import com.mailjet.client.MailjetClient;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class MailjetConfig {

    private final MailjetProperties mailjetProperties;

    @Bean
    public MailjetClient mailjetClient() {
        return new MailjetClient(mailjetProperties.getApiKey(), mailjetProperties.getPrivateKey());
    }
}
