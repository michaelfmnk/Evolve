package com.dreamteam.api.controllers;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping(Api.ROOT)
public class CommonsController {

    @Value("${app.version:unknown}")
    private String version;

    @GetMapping(value = Api.Commons.GIT_LOG, produces = MediaType.TEXT_HTML_VALUE)
    public String getGitLog() {
        try {
            return Files.toString(new File("gitlog.html"), Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return "unknown";
        }
    }

    @GetMapping(Api.Commons.VERSION)
    public String getVersion() {
        return this.version;
    }

}
