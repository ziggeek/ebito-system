package com.ebito.cloud.service.Impl;

import com.ebito.cloud.mapper.DocumentMapper;
import com.ebito.cloud.model.entity.DocumentEntity;
import com.ebito.cloud.model.response.DocumentResponse;
import com.ebito.cloud.model.response.ListDocumentResponse;
import com.ebito.cloud.reposytory.DocumentRepository;
import com.ebito.cloud.service.DocumentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CloudServiceImplTest {

    @Mock
    private DocumentRepository documentRepo;
    @Mock
    private DocumentMapper documentMapper;
    @Mock
    private DocumentService documentService;

    @InjectMocks
    private CloudServiceImpl serviceUnderTest;

    @Test
    @DisplayName("Успешное сохранение документа")
    void testSaveDocument_success() {
        String clientId = "123";
        MultipartFile file = mock(MultipartFile.class);
        DocumentEntity expectedDocument = new DocumentEntity("Тест", "Тест.pdf");
        DocumentResponse expectedResponse = DocumentResponse.builder()
                .link("https://example.com/12345.pdf")
                .name("Выписка по начислениям абонента")
                .build();

        when(documentService.upload(any(MultipartFile.class), anyString())).thenReturn(expectedDocument);
        when(documentMapper.toDto(expectedDocument, documentService)).thenReturn(expectedResponse);

        DocumentResponse response = serviceUnderTest.saveDocument(clientId, file);

        assertEquals(expectedResponse, response);
        verify(documentService).upload(any(MultipartFile.class), anyString());
        verify(documentRepo).save(expectedDocument);
        Mockito.verifyNoMoreInteractions(documentService, documentMapper, documentRepo);
    }

    @Test
    @DisplayName("Успешное получение документов")
    void testGetClientDocuments_success() {
        String clientId = "123";
        DocumentResponse expectedResponse = DocumentResponse.builder()
                .link("https://example.com/12345.pdf")
                .name("Выписка по начислениям абонента")
                .build();
        List<DocumentEntity> expectedDocuments = List.of(new DocumentEntity());
        List<DocumentResponse> expectedResponses = List.of(expectedResponse);
        ListDocumentResponse  expectedResponseList = ListDocumentResponse.builder()
                .documents(expectedResponses)
                .build();

        when(documentRepo.findAllByClientId(clientId)).thenReturn(expectedDocuments);
        when(documentMapper.toDto(any(DocumentEntity.class), any(DocumentService.class)))
                .thenReturn(expectedResponse);

        ListDocumentResponse response = serviceUnderTest.getClientDocuments(clientId);

        assertEquals(expectedResponseList, response);
        verify(documentRepo).findAllByClientId(clientId);
        verify(documentMapper, times(expectedDocuments.size())).toDto(any(DocumentEntity.class), any(DocumentService.class));
        Mockito.verifyNoMoreInteractions(documentService, documentMapper, documentRepo);
    }

    @Test
    @DisplayName("Негативный тест для saveDocument с некорректным клиентским ID")
    void testSaveDocument_invalidClientId() {
        MultipartFile file = mock(MultipartFile.class);
        // Null ID
        Exception exception1 = Assertions.assertThrows(IllegalArgumentException.class, () -> serviceUnderTest.saveDocument(null, file));
        verifyNoInteractions(documentService, documentMapper, documentRepo);
        assertEquals("Client ID cannot be empty", exception1.getMessage());

        // Пустой ID
        Exception exception2 = Assertions.assertThrows(IllegalArgumentException.class, () -> serviceUnderTest.saveDocument("", file));
        verifyNoInteractions(documentService, documentMapper, documentRepo);
        assertEquals("Client ID cannot be empty", exception2.getMessage());
    }

    @Test
    @DisplayName("Негативный тест для saveDocument с MultipartFile")
    void testSaveDocument_invalidCFile() {
        String clientId = "123";
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> serviceUnderTest.saveDocument(clientId, null));
        verifyNoInteractions(documentService, documentMapper, documentRepo);
        assertEquals("Document must not be null", exception1.getMessage());
    }

    @Test
    @DisplayName("Негативный тест для getClientDocuments с некорректным клиентским ID")
    void testGetClient_invalidClientId() {
        // Null ID
        Exception exception1 = Assertions.assertThrows(IllegalArgumentException.class, () -> serviceUnderTest.getClientDocuments(null));
        verifyNoInteractions(documentService, documentMapper, documentRepo);
        assertEquals("Client ID cannot be empty", exception1.getMessage());

        // Пустой ID
        Exception exception2 = Assertions.assertThrows(IllegalArgumentException.class, () -> serviceUnderTest.getClientDocuments(""));
        verifyNoInteractions(documentService, documentMapper, documentRepo);
        assertEquals("Client ID cannot be empty", exception2.getMessage());
    }


}

