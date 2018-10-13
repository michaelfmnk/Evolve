package com.evolvestage.docsapi;

import com.evolvestage.docsapi.properties.AuthProperties;
import com.evolvestage.docsapi.properties.BasicAuthProperties;
import com.evolvestage.docsapi.properties.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({StorageProperties.class, AuthProperties.class, BasicAuthProperties.class})
public class DocsApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DocsApiApplication.class, args);
    }
}
