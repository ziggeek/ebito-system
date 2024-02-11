package com.ebito.cloud.service.Impl;

import com.ebito.cloud.mapper.DocumentMapper;
import com.ebito.cloud.model.entity.DocumentEntity;
import com.ebito.cloud.model.response.DocumentResponse;
import com.ebito.cloud.model.response.ListDocumentResponse;
import com.ebito.cloud.reposytory.DocumentRepository;
import com.ebito.cloud.service.CloudService;
import com.ebito.cloud.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CloudServiceImpl implements CloudService {

    private final DocumentRepository documentRepo;
    private final DocumentMapper documentMapper;
    private final DocumentService documentService;

    @Override
    public DocumentResponse saveDocument(final String clientId,
                                         final MultipartFile file) {
        Assert.notNull(file, "Document must not be null");
        Assert.hasText(clientId, "Client ID cannot be empty");
        log.info("Creating document for client: {}", clientId);
        DocumentEntity document = documentService.upload(file, clientId);
        documentRepo.save(document);
        return documentMapper.toDto(document, documentService);
    }


    @Override
    public ListDocumentResponse getClientDocuments(final String clientId) {
        Assert.hasText(clientId, "Client ID cannot be empty");
        log.info("Getting document references for client: {}", clientId);
        List<DocumentEntity> documents = documentRepo.findAllByClientId(clientId);
        List<DocumentResponse> response = documents.stream().map(document -> documentMapper.toDto(document, documentService)).collect(Collectors.toList());
        return ListDocumentResponse.builder().documents(response).build();
    }
}
