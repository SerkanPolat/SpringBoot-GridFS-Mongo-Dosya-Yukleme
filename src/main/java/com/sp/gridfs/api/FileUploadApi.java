package com.sp.gridfs.api;

import com.sp.gridfs.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/upload")
public class FileUploadApi {
    @Autowired
    FileUploadService fileUploadService;
    @PostMapping
    public void FileUpload(@RequestParam MultipartFile file){
        fileUploadService.UploadFile(file);
    }
    @GetMapping
    public ResponseEntity<Resource> getFile(@RequestParam String fileName) throws IOException {
        return fileUploadService.getFileFor(fileName);
    }
}
