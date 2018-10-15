package com.evolvestage.docsapi.services;

import com.evolvestage.docsapi.properties.StorageProperties;
import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import static java.nio.file.Files.*;

@Component
@CommonsLog
@AllArgsConstructor
public class StorageUtils {

    private static final int BYTES_IN_KB = 1024;
    private final StorageProperties storageProperties;

    public double bytesToKb(long bytes) {
        return bytes / BYTES_IN_KB;
    }

    public boolean checkFileFormat(String fileExtension) {
        return CollectionUtils.isEmpty(storageProperties.getAcceptedFileFormats())
                || storageProperties.getAcceptedFileFormats().contains(fileExtension.toLowerCase());
    }

    public boolean checkFileNameLength(String fileBaseName) {
        return fileBaseName.length() <= storageProperties.getMaxNameLength();
    }

    public void moveFileToPermanentLocation(UUID fileId) throws IOException {
        Path pathToTemporaryFile = Paths
                .get(storageProperties.getTemporaryLocation())
                .resolve(fileId.toString());
        Path pathToPermanentFile = Paths
                .get(storageProperties.getPermanentLocation())
                .resolve(fileId.toString());
        move(pathToTemporaryFile, pathToPermanentFile, StandardCopyOption.REPLACE_EXISTING);
    }

    public byte[] getFile(UUID fileId) throws IOException {
        Path pathToTemporaryFile = Paths
                .get(storageProperties.getTemporaryLocation())
                .resolve(fileId.toString());
        Path pathToPermanentFile = Paths
                .get(storageProperties.getPermanentLocation())
                .resolve(fileId.toString());

        if (exists(pathToTemporaryFile)) {
            log.info(String.format("File is in temporary storage: fileId = %s", fileId));
            return readAllBytes(pathToTemporaryFile);
        } else if (exists(pathToPermanentFile)) {
            log.info(String.format("File is in permanent storage: fileId = %s", fileId));
            return readAllBytes(pathToPermanentFile);
        }
        log.warn(String.format("File fileId = %s, %s does not exists", fileId, pathToPermanentFile.toString()));
        return new byte[0];
    }

    public void saveFileInTemporaryLocation(MultipartFile file, String name) throws IOException {
        Path pathToTmpLocation = Paths.get(storageProperties.getTemporaryLocation());
        saveInDir(file.getInputStream(), name, pathToTmpLocation);
    }

    private void saveInDir(InputStream inputStream, String name, Path pathToDir) throws IOException {
        Path path = pathToDir.resolve(name);
        Files.copy(inputStream, path);
    }

    public void deleteFile(UUID fileId) {
        try {
            deleteTemporaryFile(fileId);
            Path pathToPermanentFile = Paths
                    .get(storageProperties.getPermanentLocation())
                    .resolve(fileId.toString());
            deleteIfExists(pathToPermanentFile);
        } catch (IOException e) {
            log.error(String.format("there was an error while trying to delete file with fileId=%s", fileId), e);
        }
    }

    public void deleteTemporaryFile(UUID fileId) throws IOException {
        Path pathToTemporaryFile = Paths
                .get(storageProperties.getTemporaryLocation())
                .resolve(fileId.toString());
        deleteIfExists(pathToTemporaryFile);
    }

}
