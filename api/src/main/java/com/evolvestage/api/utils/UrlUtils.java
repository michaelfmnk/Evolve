package com.evolvestage.api.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.apachecommons.CommonsLog;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@CommonsLog
@UtilityClass
public class UrlUtils {

    private final String HTTP = "http";
    private final String DOCS_API_URL= "evolve-stage.com";
    private final String PUBLIC_FILE_PATH_TEMPLATE = "/docs-api/permanent/public/%s";

    public String formBackgroundUrl(UUID backgroundId) {
        String path = String.format(PUBLIC_FILE_PATH_TEMPLATE, backgroundId);
        try {
            URI uri = new URI(HTTP, null, DOCS_API_URL, 80, path, null, null);
            return uri.toURL().toString();
        } catch (URISyntaxException | MalformedURLException e) {
            log.error("there was an error while forming an url for background", e);
        }
        return null;
    }
}
