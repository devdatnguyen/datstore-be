package com.example.DatStore.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Value("${uploads.dir}")
    private String UPLOAD_DIR;

    @Override
    public ResponseEntity<Map<String, String>> updateFile(MultipartFile file, String oldFile) throws IOException {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "File is empty"));
        }

        if (oldFile != null && !oldFile.isEmpty() && !oldFile.equals("undefined")) {
            Path oldFilePath = Paths.get(UPLOAD_DIR, oldFile.substring(oldFile.lastIndexOf("/") + 1));
            Files.deleteIfExists(oldFilePath);
        }

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR, fileName);
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, file.getBytes());

        return ResponseEntity.ok(Map.of("fileUrl", "/"+UPLOAD_DIR+"/" + fileName));
    }
}
