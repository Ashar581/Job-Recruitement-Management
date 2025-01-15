package com.ashar.job.recruitment.management.Dto;

import com.ashar.job.recruitment.management.Entity.Document;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class DocumentDto {
    private UUID uuid;
    private Map<String,Object> metadata;
    private String fileName;
    private byte [] data;
    private String fileType;

    public static Document dtoToEntity(DocumentDto dto){
        Document entity = new Document();
        entity.setData(dto.getData());
        entity.setMetadata(dto.getMetadata());
        entity.setFileName(dto.getFileName());
        entity.setFileType(dto.getFileType());
        entity.setUuid(dto.getUuid());

        return entity;
    }
    public static DocumentDto entityToDto(Document entity){
        DocumentDto dto = new DocumentDto();
        dto.setUuid(entity.getUuid());
//        dto.setData(entity.getData());
        dto.setFileType(entity.getFileType());
        dto.setFileName(entity.getFileName());
        dto.setMetadata(entity.getMetadata());

        return dto;
    }
}
