package com.ebito.cloud.service;

import com.ebito.cloud.model.response.DocumentResponse;
import com.ebito.cloud.model.response.ListDocumentResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CloudService {
    /**
     * Сохраняет всю нужную информацию в БД
     *
     * @param clientId идентификатор клиента, которому относится документ или документы. Используется String
     * @param file     документ или документы для загрузки. Используется MultipartFile.
     * @return объект PrintedGuids, который содержит идентификаторы документов, загруженных клиентом
     */
    DocumentResponse saveDocument(String clientId, MultipartFile file);

    /**
     * Возвращает список идентификаторов документов, загруженных клиентом.
     *
     * @param clientId идентификатор клиента. Используется String.
     * @return список объектов PrintedGuids, который содержит идентификаторы документов, загруженных клиентом.
     */
    ListDocumentResponse getClientDocuments(String clientId);
}
