package com.evolvestage.api.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.Objects;
import java.util.UUID;

@CommonsLog
@UtilityClass
public class UrlUtils {

    private final String HTTP = "http";
    private final String DOCS_API_URL= "evolve-stage.com";
    private final String PUBLIC_FILE_PATH_TEMPLATE = "/docs-api/permanent/public/%s";

    public String formPublicFileUrl(UUID backgroundId) {
        if (Objects.isNull(backgroundId)) {
            return null;
        }

        String path = String.format(PUBLIC_FILE_PATH_TEMPLATE, backgroundId);
        try {
            URI uri = UriComponentsBuilder.fromPath(path)
                    .scheme(HTTP)
                    .host(DOCS_API_URL)
                    .build().toUri();
            return uri.toURL().toString();
        } catch (MalformedURLException e) {
            log.error("there was an error while forming an url for background", e);
        }
        return null;
    }

    public String formAcceptInvitationUrl(UUID uuid) {
        return "http://evolve-stage.com/invitations/" + uuid.toString();
    }
}
