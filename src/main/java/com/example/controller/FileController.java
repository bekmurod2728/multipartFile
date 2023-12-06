package com.example.controller;

import com.example.dto.FileDto;
import com.example.service.FileService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/file")
public class FileController {
    private final FileService service;

    public FileController(FileService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public ResponseEntity<FileDto> upload(@RequestPart("file") MultipartFile multipartFile) throws IOException {
                return service.upload(multipartFile);
    }
@GetMapping("/download/{id}")
    public ResponseEntity<?> download(@PathVariable String id) throws IOException {
       return service.download(id);
    }
    @GetMapping("/preview/{id}")
    public ResponseEntity<?> preview(@PathVariable String id) throws IOException {
       return service.preview(id);
    }
}
