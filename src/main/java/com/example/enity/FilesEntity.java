package com.example.enity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FilesEntity {
    @Id
    String id;
    String name;
    String contentType;
    Long size;
    String type;
    String path;

    public FilesEntity(MultipartFile multipartFile){
        String[] split = multipartFile.getContentType().split("/");
        LocalDate date=LocalDate.now();
        UUID uuid=UUID.randomUUID();
        setId(uuid.toString());
        setName(multipartFile.getOriginalFilename());
        setSize(multipartFile.getSize());
        setContentType(multipartFile.getContentType());
        setType(split[1]);
        String path="./file/"+date.getYear()+"/"+
                date.getMonthValue()+"/"+date.getDayOfMonth()+"/"+
                split[0]+"/"+uuid+"."+getType();
        setPath(path);
    }

    }
