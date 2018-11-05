package com.evolvestage.docsapi.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties("storage")
public class StorageProperties {

    private String temporaryLocation;
    private String permanentLocation;
    private List<String> acceptedMimes;
    private List<String> acceptedFileFormats;
    private Integer maxNameLength;

}
