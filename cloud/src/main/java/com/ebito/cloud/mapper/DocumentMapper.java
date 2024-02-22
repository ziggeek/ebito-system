package com.ebito.cloud.mapper;

import com.ebito.cloud.model.entity.DocumentEntity;
import com.ebito.cloud.model.response.DocumentResponse;
import com.ebito.cloud.service.DocumentService;
import org.mapstruct.*;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface DocumentMapper {
    /**
     * Маппинг DocumentEntity в DocumentResponse
     * @param document объект DocumentEntity
     * @param documentService сервис документов для получения ссылки
     * @return объект DocumentResponse
     */

    @Mapping(target = "link", source = "fileName", qualifiedByName = "documentMapper")
    @Mapping(target = "name", source = "fileName")
    DocumentResponse toDto(DocumentEntity document, @Context DocumentService documentService);

    /**
     * Метод для создания ссылки на документ по имени файла
     * @param fileName имя файла
     * @param documentService сервис документов
     * @return ссылка на документ
     */
    @Named("documentMapper")
    default String documentMapper(String fileName, @Context DocumentService documentService) {
        return documentService.downloadUrl(fileName);
    }

}