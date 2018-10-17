package com.evolvestage.api.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("auth")
public class AuthProperties {
    private int expiration;
    private String headerName;
    private String privateKey;
    private String publicKey;
    private Integer codeLen;
}
