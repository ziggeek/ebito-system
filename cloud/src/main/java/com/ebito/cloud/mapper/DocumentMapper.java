package com.ebito.cloud.mapper;

import com.ebito.cloud.model.entity.DocumentEntity;
import com.ebito.cloud.model.response.DocumentResponse;
import com.ebito.cloud.service.DocumentService;
import org.mapstruct.*;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface DocumentMapper {


    @Mapping(target = "link", source = "fileName", qualifiedByName = "documentMapper")
    @Mapping(target = "name", source = "fileName")
    DocumentResponse toDto(DocumentEntity document, @Context DocumentService documentService);


    @Named("documentMapper")
    default String documentMapper(String fileName, @Context DocumentService documentService) {
        return documentService.downloadUrl(fileName);
    }

}