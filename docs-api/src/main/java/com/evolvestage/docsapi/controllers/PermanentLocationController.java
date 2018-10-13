package com.evolvestage.docsapi.controllers;

import com.evolvestage.docsapi.services.StorageService;
import com.evolvestage.docsapi.dtos.DocumentDto;
import com.evolvestage.docsapi.dtos.DocumentResponseDto;
import com.evolvestage.docsapi.dtos.MoveDocumentDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping(Api.ROOT_PATH)
public class PermanentLocationController {

    private final StorageService storageService;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(Api.PermanentStorage.PERMANENT_LOCATION)
    public void deleteFiles(@RequestParam("file_id") Set<UUID> fileIds) {
        storageService.deleteFiles(fileIds);
    }

    @GetMapping(Api.PermanentStorage.PERMANENT_FILE_BY_ID)
    public ResponseEntity<byte[]> downloadFile(@PathVariable("file_id") UUID fileId) throws IOException {
        DocumentResponseDto responseDto = storageService.downloadFile(fileId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(responseDto.getMime()));
        return new ResponseEntity<>(responseDto.getFile(), headers, HttpStatus.OK);
    }

    @GetMapping(Api.PermanentStorage.PERMANENT_LOCATION)
    public List<DocumentDto> getFilesInfo(@RequestParam("file_id") Set<UUID> fileId) {
        return storageService.getFiles(fileId);
    }

    @PutMapping(Api.PermanentStorage.PERMANENT_LOCATION)
    public List<DocumentDto> moveFileToPermanentStorage(
            @Validated @NotEmpty @RequestBody List<MoveDocumentDto> filesToMove) throws IOException {
        return storageService.moveFiles(filesToMove);
    }
}


