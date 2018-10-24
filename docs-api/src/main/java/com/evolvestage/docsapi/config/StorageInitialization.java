package com.evolvestage.docsapi.config;

import com.evolvestage.docsapi.properties.StorageProperties;
import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.Files.createDirectories;
import static java.nio.file.Files.exists;

@Component
@CommonsLog
@AllArgsConstructor
public class StorageInitialization implements ApplicationListener<ApplicationReadyEvent> {
    private final StorageProperties storageProperties;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        try {
            Path tmpLocation = Paths.get(storageProperties.getTemporaryLocation());
            Path permanentLocation = Paths.get(storageProperties.getTemporaryLocation());

            if (!exists(tmpLocation)) {
                createDirectories(tmpLocation);
                log.info("creating temporary directory");
            }
            if (!exists(tmpLocation)) {
                createDirectories(permanentLocation);
                log.info("creating permanent directory");
            }
        } catch (IOException e) {
            log.error("couldn't create directory", e);
        }
    }
}
