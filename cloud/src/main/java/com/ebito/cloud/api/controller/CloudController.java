package com.ebito.cloud.api.controller;

import com.ebito.cloud.api.CloudApi;
import com.ebito.cloud.model.response.DocumentResponse;
import com.ebito.cloud.model.response.ListDocumentResponse;
import com.ebito.cloud.service.CloudService;
import com.ebito.cloud.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CloudController implements CloudApi {

    private final CloudService cloudService;
    private final DocumentService documentService;

    @Override
    public ResponseEntity<DocumentResponse> saveClientDocument(final MultipartFile file,
                                                               final String clientId,
                                                               final String checksum) {
        DocumentResponse response = cloudService.saveDocument(clientId, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @Override
    public ResponseEntity<ListDocumentResponse> getClientDocuments(final String clientId) {
        ListDocumentResponse clientDocuments = cloudService.getClientDocuments(clientId);
        return ResponseEntity.status(HttpStatus.OK).body(clientDocuments);
    }

    @Override
    public ResponseEntity<String> getDocumentLinkByName(final String name) {
        String url = documentService.downloadUrl(name);
        return ResponseEntity.status(HttpStatus.OK).body(url);
    }

}
