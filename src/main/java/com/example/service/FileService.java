package com.example.service;

import com.example.dto.FileDto;
import com.example.enity.FilesEntity;
import com.example.repository.FileRepository;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FileService {
    private final FileRepository repository;

    public FileService(FileRepository repository) {
        this.repository = repository;
    }

//    public ResponseEntity<List<FileDto>> getAll(){
//        List<FileDto> collect = repository.findAll().stream().map(FileDto::changeToDto).collect(Collectors.toList());
//        return ResponseEntity.ok(collect);
//    }

//    public ResponseEntity<FileDto> findById(String id){
//        FilesEntity filesEntity = repository.findById(id).orElse(null);
//        if (filesEntity!=null){
//            return ResponseEntity.ok(FileDto.changeToDto(filesEntity));
//        }
//        return ResponseEntity.notFound().build();
//    }

    public ResponseEntity<FileDto> upload(MultipartFile multipartFile){
        FilesEntity entity=new FilesEntity(multipartFile);
        File file=new File(entity.getPath());
        file.getParentFile().mkdirs();
        file=file.getAbsoluteFile();
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        repository.save(entity);
        return ResponseEntity.ok(FileDto.changeToDto(entity));
    }
    public ResponseEntity<?> download(String id) throws IOException {
        Optional<FilesEntity> byId = repository.findById(id);
        if (byId.isPresent()){
            FileInputStream inputStream= new FileInputStream(byId.get().getPath());
            InputStreamResource resource=new InputStreamResource(inputStream);
            return ResponseEntity.ok()
                    .header("Content-Disposition",("attachment; filename=\""+ byId.get().getName()+"\""))
                    .contentType(MediaType.parseMediaType(byId.get().getContentType()))
                    .body(resource);

        }
        return ResponseEntity.notFound().build();
    }
    public ResponseEntity<?> preview(String id) throws IOException {
        Optional<FilesEntity> byId = repository.findById(id);
        if (byId.isPresent()){
            FileInputStream inputStream= new FileInputStream(byId.get().getPath());
            InputStreamResource resource=new InputStreamResource(inputStream);
            return ResponseEntity.ok()
                    .header("Content-Disposition",("inline; filename=\""+ byId.get().getName()+"\""))
                    .contentType(MediaType.parseMediaType(byId.get().getContentType()))
                    .body(resource);

        }
        return ResponseEntity.notFound().build();
    }

//    public ResponseEntity<FileDto> deleteById(String id){
//        FilesEntity entity = repository.findById(id).orElse(null);
//        if (entity==null){
//            return ResponseEntity.notFound().build();
//        }
//        repository.deleteById(id);
//        return ResponseEntity.ok(FileDto.changeToDto(entity));
//    }
}
