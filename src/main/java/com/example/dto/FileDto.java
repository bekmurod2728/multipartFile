package com.example.dto;

import com.example.enity.FilesEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileDto {
    String name;
    String contentType;
    Long size;
    String type;

    public static FileDto changeToDto(FilesEntity filesEntity){
        FileDto dto=new FileDto();
        dto.setName(filesEntity.getName());
        dto.setSize(filesEntity.getSize());
        dto.setType(filesEntity.getType());
        dto.setContentType(filesEntity.getContentType());
        return dto;
    }
}
