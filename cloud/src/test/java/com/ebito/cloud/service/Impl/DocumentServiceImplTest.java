package com.ebito.cloud.service.Impl;


import com.ebito.cloud.config.TestConfig;
import com.ebito.cloud.model.entity.DocumentEntity;
import com.ebito.cloud.properties.CloudProperties;
import com.ebito.exceptionhandler.exception.BucketProcessingException;
import com.ebito.exceptionhandler.exception.FileProcessingException;
import io.minio.MinioClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Import(TestConfig.class)
@ExtendWith(MockitoExtension.class)
class DocumentServiceImplTest {


    @MockBean
    private CloudProperties cloudProperties;
    @MockBean
    public MinioClient minioClient;
    @Autowired
    private DocumentServiceImpl documentService;


    @Test
    @DisplayName("Тестирует загрузку файла в MinIO")
    void uploadValidFile() {
        String clientId = "123";
        String fileName = "test-file.pdf";
        MultipartFile file = new MockMultipartFile("file", fileName, "application/pdf", "test content".getBytes());
        when(cloudProperties.getBucket()).thenReturn("document");
        DocumentEntity result = documentService.upload(file, clientId);

        assertNotNull(result);
        assertEquals(clientId, result.getClientId());
        assertEquals(fileName, result.getFileName());
    }

    @Test
    @DisplayName("Тестирование выдача ссылки из Minio")
    public void testDownloadUrlSuccess() throws Exception {
        String expectedUrl = "https://example.com/document.pdf";
        when(minioClient.getPresignedObjectUrl(any())).thenReturn(expectedUrl);
        when(cloudProperties.getBucket()).thenReturn("document");

        String actualUrl = documentService.downloadUrl(("test-document"));
        assertNotNull(actualUrl);
        verify(minioClient).getPresignedObjectUrl(any());
        verify(cloudProperties).getBucket();
        assertEquals(expectedUrl, actualUrl);
    }

    @Test
    @DisplayName("Негативный тест для upload с некорректным bucket")
    void testUpload_invalidBucket() {
        when(cloudProperties.getBucket()).thenReturn(null);

        Exception exception = Assertions.assertThrows(BucketProcessingException.class, ()
                -> documentService.upload(mock(MultipartFile.class), "123"));

        assertEquals("Bucket failed: bucket name must not be null.", exception.getMessage());
        verify(cloudProperties, times(2)).getBucket();
    }

    @Test
    @DisplayName("Тестирует обработку исключения при ошибке получения InputStream")
    void testFileProcessingException() throws Exception {

        MultipartFile file = mock(MultipartFile.class);
        when(file.getInputStream()).thenThrow(new FileProcessingException("Document upload failed:"));

        Exception exception = Assertions.assertThrows(FileProcessingException.class, file::getInputStream);
        assertEquals("Document upload failed:", exception.getMessage());
    }


}
