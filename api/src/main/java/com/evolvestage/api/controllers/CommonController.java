package com.evolvestage.api.controllers;

import com.evolvestage.api.dtos.CommonBackgroundDto;
import com.evolvestage.api.dtos.Pagination;
import com.evolvestage.api.services.CommonBackgroundService;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping(Api.ROOT)
@RequiredArgsConstructor
public class CommonController {

    @Value("${app.version:unknown}")
    private String version;
    private final CommonBackgroundService backgroundService;

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

    @GetMapping(Api.Commons.BACKGROUNDS)
    public Pagination<CommonBackgroundDto> getBackgrounds(@PageableDefault(size = 20) Pageable pageable) {
        return backgroundService.getCommonBackground(pageable);
    }

}
