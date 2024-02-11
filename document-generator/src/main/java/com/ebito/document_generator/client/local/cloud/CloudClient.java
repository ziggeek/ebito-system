package com.ebito.document_generator.client.local.cloud;

import com.ebito.document_generator.client.local.cloud.response.DocumentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(
        value = "cloud",
        url = "${app.feign.client.config.cloud.url}",
        path = "/api/v1/")
public interface CloudClient {

    /**
     * Сохранить сгенерированный документ в облаке
     * @param clientId - ид-клиента
     * @param checksum - чек-сумма документа
     * @param document - файл сгенерированного документа
     * @return
     */
    @PostMapping("/{clientId}/documents/save-client-document")
    ResponseEntity<DocumentResponse> saveDocument(
            @PathVariable String clientId,
            @RequestParam(name = "checksum") String checksum,
            @RequestPart(name = "file") MultipartFile document);

}
