package com.ebito.cloud.api.controller;

import com.ebito.cloud.model.response.DocumentResponse;
import com.ebito.cloud.model.response.ListDocumentResponse;
import com.ebito.cloud.service.CloudService;
import com.ebito.cloud.service.DocumentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CloudControllerTest {

    @Mock
    private CloudService cloudService;
    @Mock
    private DocumentService documentService;

    @InjectMocks
    private CloudController cloudController;

    @Test
    @DisplayName("Given correct request " +
            "When call endpoint GET /downloadUrl " +
            "Then return 200 http code and valid DocumentResponse")
    void testGetUrlByNameFile() {
        String name = "Выписка по начислениям абонента";
        String expectedLink = "https://example.com/12345.pdf";
        when(documentService.downloadUrl(name)).thenReturn(expectedLink);

        ResponseEntity<String> response = cloudController.getDocumentLinkByName(name);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedLink, response.getBody());
        verify(documentService).downloadUrl(name);
    }

    @Test
    @DisplayName("Given correct request " +
            "When call endpoint POST /saveClientDocument " +
            "Then return 201 http code and valid DocumentResponse")
    void testSaveClientDocument() {
        String clientId = "1";
        String checksum = "abc123";
        MultipartFile file = mock(MultipartFile.class);

        DocumentResponse expectedResponse = DocumentResponse.builder()
                .link("https://example.com/12345.pdf")
                .name("Выписка по начислениям абонента1")
                .build();
        when(cloudService.saveDocument(clientId, file)).thenReturn(expectedResponse);

        ResponseEntity<DocumentResponse> response = cloudController.saveClientDocument(file, clientId, checksum);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
        verify(cloudService).saveDocument(clientId, file);
    }

    @Test
    @DisplayName("Given correct request " +
            "When call endpoint GET /getClientDocuments " +
            "Then return 200 http code and list of DocumentResponse")
    void testGetClientDocuments() {
        String clientId = "1";
        DocumentResponse expectedResponse1 = DocumentResponse.builder()
                .link("https://example.com/12345.pdf")
                .name("Выписка по начислениям абонента1")
                .build();
        DocumentResponse expectedResponse2 = DocumentResponse.builder()
                .link("https://example.com/56789.pdf")
                .name("Выписка по начислениям абонента2")
                .build();
        List<DocumentResponse> expectedResponses = Arrays.asList(
                expectedResponse1, expectedResponse2
        );
        ListDocumentResponse  expectedResponse = ListDocumentResponse.builder()
                .documents(expectedResponses)
                .build();
        when(cloudService.getClientDocuments(clientId)).thenReturn(expectedResponse);
        ResponseEntity<ListDocumentResponse> response = cloudController.getClientDocuments(clientId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
        verify(cloudService).getClientDocuments(clientId);
    }


}

