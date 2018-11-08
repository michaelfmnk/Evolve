package com.evolvestage.api.docs;

import com.evolvestage.api.exceptions.BadRequestException;
import com.evolvestage.api.exceptions.ForbiddenException;
import com.evolvestage.api.exceptions.UnprocessableEntityException;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
@CommonsLog
public class DocsApiService {

    @Value("${docs.api.baseUrl}")
    private String baseUrl;
    @Value("${auth.headerName}")
    private String authHeaderName;

    private final String PERMANENT_DOCS_PATH = "/permanent";

    private HttpServletRequest request;
    private final RestTemplate restTemplate;

    public DocsApiService(HttpServletRequest request, RestTemplate restTemplate) {
        this.request = request;
        this.restTemplate = restTemplate;
    }

    public void moveToPermanentStorage(UUID fileId, Integer dataId, boolean makePublic) {
        MoveDocumentDto moveDocumentDto = MoveDocumentDto.builder()
                .dataId(dataId)
                .fileId(fileId)
                .isPublic(makePublic)
                .build();
        HttpHeaders headers = getHeaders();
        HttpEntity<List<MoveDocumentDto>> requestEntity = new HttpEntity<>(Arrays.asList(moveDocumentDto), headers);
        log.info("started performing request to docs-api to save file with id = " + fileId);
        performRequest(() -> restTemplate.exchange(
                formatPath(PERMANENT_DOCS_PATH),
                HttpMethod.PUT,
                requestEntity,
                new ParameterizedTypeReference<List<DocumentDto>>() {}));
    }

    public void deleteDocument(UUID fileId) {
        String url = UriComponentsBuilder.fromHttpUrl(formatPath(PERMANENT_DOCS_PATH))
                .queryParam("file_id", fileId)
                .toUriString();
        HttpEntity<?> requestEntity = new HttpEntity<>(getHeaders());
        log.info("started performing request to docs-api to delete file with id = " + fileId);
        performRequest(() -> restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, String.class));
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(authHeaderName, request.getHeader(authHeaderName));
        return headers;
    }

    private String formatPath(String path) {
        return baseUrl + path;
    }

    private void performRequest(Runnable request) {
        try {
            request.run();
        } catch (HttpStatusCodeException e) {
            ResponseEntity errorResponse = new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
            log.warn("Something wrong when send request to docs", e);
            rethrow(errorResponse);
        } catch (RestClientException e) {
            log.warn("Something wrong when send request to docs", e);
            throw new UnprocessableEntityException("Something wrong when send request to docs");
        }
    }

    private void rethrow(ResponseEntity<?> response) {
        if (Objects.isNull(response)) {
            return;
        }
        String body = Optional.ofNullable(response.getBody())
                .map(Object::toString)
                .orElse(Strings.EMPTY);
        switch (response.getStatusCode()) {
            case OK:
            case NO_CONTENT:
                return;
            case BAD_REQUEST:
                throw new BadRequestException(body);
            case FORBIDDEN:
                throw new ForbiddenException(body);
            case NOT_FOUND:
                throw new EntityNotFoundException(body);
            default:
                throw new UnprocessableEntityException(body);
        }
    }

}
