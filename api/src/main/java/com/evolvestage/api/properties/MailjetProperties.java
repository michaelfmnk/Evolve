package com.evolvestage.api.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("mail")
public class MailjetProperties {
    private String apiKey;
    private String privateKey;
    private String senderEmail;
    private String senderName;
}
