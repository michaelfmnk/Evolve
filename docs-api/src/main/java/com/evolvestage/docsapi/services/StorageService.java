package com.evolvestage.docsapi.services;

import com.evolvestage.docsapi.dtos.DocumentDto;
import com.evolvestage.docsapi.dtos.DocumentResponseDto;
import com.evolvestage.docsapi.dtos.MoveDocumentDto;
import com.evolvestage.docsapi.entities.Document;
import com.evolvestage.docsapi.exceptions.BadRequestException;
import com.evolvestage.docsapi.repositories.DocumentRepository;
import com.google.common.io.Files;
import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@CommonsLog
@AllArgsConstructor
public class StorageService {
    private final StorageUtils storageUtils;
    private final DocumentRepository documentRepository;
    private final ConverterService converterService;

    @Transactional
    public void deleteTemporaryFile(UUID fileId) throws IOException {
        log.info(format("deleting tmp-file: file_id = %s", fileId));
        Document doc = documentRepository.findByFileIdAndAndDataIdIsNull(fileId)
                .orElseThrow(() -> new EntityNotFoundException("Document not found"));
        storageUtils.deleteTemporaryFile(fileId);
        documentRepository.delete(doc);
    }

    public void deleteFiles(Set<UUID> fileIds) {
        log.info(format("Deleting files, ids = %s", fileIds));
        List<Document> docs = documentRepository.findByFileIdIn(fileIds);
        if (!Objects.equals(fileIds.size(), docs.size())) {
            throw new EntityNotFoundException("No documents were found");
        }
        documentRepository.deleteInBatch(docs);
        fileIds.forEach(storageUtils::deleteFile);
    }

    private void failIfFileNotValid(String fileName) {
        if (!storageUtils.checkFileFormat(Files.getFileExtension(fileName))) {
            throw new BadRequestException("not acceptable file type");
        }
        if (!storageUtils.checkFileNameLength(fileName)) {
            throw new BadRequestException("file name is too long");
        }
    }

    public DocumentDto uploadFile(MultipartFile file) throws IOException, TikaException, SAXException {
        String fileBaseName = FilenameUtils.getBaseName(file.getOriginalFilename());
        String mime = getMime(file);

        log.info(format("Uploaded file with filename=%s and type=%s", fileBaseName, file.getContentType()));

        failIfFileNotValid(fileBaseName);

        UUID uuid = UUID.randomUUID();
        Document docToUpload = Document.builder()
                .fileId(uuid)
                .mime(mime)
                .documentName(fileBaseName)
                .size(BigDecimal.valueOf(storageUtils.bytesToKb(file.getSize())))
                .build();

        storageUtils.saveFileInTemporaryLocation(file, uuid.toString());
        docToUpload = documentRepository.save(docToUpload);
        return converterService.toDto(docToUpload);
    }

    private String getMime(MultipartFile file) throws IOException, TikaException, SAXException {
        Metadata meta = new Metadata();
        try (InputStream inputStream = file.getInputStream()) {
            new AutoDetectParser().parse(inputStream, new BodyContentHandler(), meta);
        }
        return meta.get(Metadata.CONTENT_TYPE);
    }

    public DocumentResponseDto downloadFile(UUID fileId) throws IOException {
        Document doc = documentRepository.findByFileId(fileId)
                .orElseThrow(() -> new EntityNotFoundException("Document not found"));
        byte[] file = storageUtils.getFile(fileId);
        return DocumentResponseDto.builder()
                .file(file)
                .mime(doc.getMime())
                .build();
    }

    public DocumentResponseDto downloadPublicFile(UUID fileId) throws IOException {
        Document doc = documentRepository.findByFileIdAndIsPublicIsTrue(fileId)
                .orElseThrow(() -> new EntityNotFoundException("Document not found"));
        byte[] file = storageUtils.getFile(fileId);
        return DocumentResponseDto.builder()
                .file(file)
                .mime(doc.getMime())
                .build();
    }

    public List<DocumentDto> moveFiles(List<MoveDocumentDto> docsToMove) throws IOException {
        List<DocumentDto> fileDtos = new ArrayList<>(docsToMove.size());
        for (MoveDocumentDto item : docsToMove) {
            fileDtos.add(moveFile(item.getFileId(), item.getDataId(), item.getIsPublic()));
        }
        return fileDtos;
    }

    private DocumentDto moveFile(UUID fileId, Integer dataId, Boolean isPublic) throws IOException {
        log.info(format("moving documents from temporary storage: fileId = %s & dataId = %s", fileId, dataId));
        Document doc = documentRepository.findByFileIdAndAndDataIdIsNull(fileId)
                .orElseThrow(() -> new EntityNotFoundException("Document not found"));

        storageUtils.moveFileToPermanentLocation(fileId);
        doc.setDataId(dataId);
        doc.setIsPublic(Boolean.TRUE.equals(isPublic));
        doc = documentRepository.save(doc);
        return converterService.toDto(doc);
    }

    public List<DocumentDto> getFiles(Set<UUID> fileIds) {
        List<Document> documents = documentRepository.findByFileIdIn(fileIds);
        if (!Objects.equals(fileIds.size(), documents.size())) {
            throw new EntityNotFoundException("No documents were found");
        }
        return documents.stream()
                .map(converterService::toDto)
                .collect(Collectors.toList());

    }
}
