package com.evolvestage.docsapi.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("auth")
public class AuthProperties {
    private String headerName;
    private String publicKey;
}
