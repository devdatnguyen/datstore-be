package com.example.DatStore.utils;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface FileUploadService {
    ResponseEntity<Map<String, String>> updateFile(MultipartFile file,String oldFile) throws IOException;
}
