package com.ebito.document_generator.service;

import com.ebito.document.DocumentType;
import com.ebito.document_generator.api.controller.request.FormGenerationRequest;
import com.ebito.document_generator.api.controller.response.FormGenerationResponse;
import com.ebito.document_generator.client.local.cloud.CloudClient;
import com.ebito.document_generator.client.local.cloud.request.DocumentMultipartFile;
import com.ebito.document_generator.client.local.cloud.response.DocumentResponse;
import com.ebito.document_generator.template.FontProvider;
import com.ebito.document_generator.template.TemplateHandler;
import com.ebito.document_generator.util.FormUtils;
import com.ebito.document_generator.util.YargUtils;
import com.ebito.exceptionhandler.exception.ConvertToPdfException;
import com.ebito.exceptionhandler.exception.GeneratePrintedFormException;
import com.sun.star.util.FileIOException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@Service
@RequiredArgsConstructor
public class FormGeneratorServiceImpl implements FormGeneratorService {

    private static final String HASH_ALGORITHM_NAME = "SHA3-256";

    private final TemplateHandler templateHandler;
    private final YargUtils yargUtils;
    private final FormUtils formUtils;
    private final FontProvider fontProvider;
    private final CloudClient cloudClient;

    @Value("${app.stubs.isLocalStubEnabled:false}")
    private boolean isLocalStubEnabled;

    @SneakyThrows
    @Override
    public FormGenerationResponse generate(final String clientId,
                                           final DocumentType docType,
                                           final FormGenerationRequest formData) {
        Assert.notNull(formData, "'formData' must not be null");

        final byte[] docxBytes = generateDocxFromTemplate(formData);
        final byte[] pdfBytes = convertDocxToPdf(docxBytes);
        final byte[] documentBytes = chooseDocumentExtension(docxBytes, pdfBytes, docType);
        final String checkSum = getCheckSum(documentBytes);
        final String pdfFileName = formUtils.buildRandomPdfFileName(docType);

        FormGenerationResponse response;
        if (isLocalStubEnabled) {
            String userHome = System.getProperty("user.home");
            boolean isLinuxMachine = System.getProperty("os.name").toLowerCase().contains("linux");
            String downloadPathName = isLinuxMachine ? "download" : "Downloads";
            String downloadsDirectory = userHome + File.separator + downloadPathName;

            Files.write(Paths.get(downloadsDirectory, pdfFileName), documentBytes);
            Files.write(Paths.get(downloadsDirectory, pdfFileName.replace(".pdf", ".docx")), docxBytes);
            response = buildFormGenerationResponse(formData.getForm().getDocumentName(), checkSum, pdfFileName);
        } else {
            var document = DocumentMultipartFile.builder()
                    .bytes(documentBytes)
                    .originalFilename(pdfFileName)
                    .contentType(MediaType.APPLICATION_PDF_VALUE)
                    .build();

            var savedDocument = cloudClient.saveDocument(clientId, checkSum, document).getBody();
            response = buildFormGenerationResponse(formData.getForm().getDocumentName(), checkSum, pdfFileName, savedDocument);
        }

        return response;
    }

    private byte[] chooseDocumentExtension(byte[] docxBytes,
                                           byte[] pdfBytes,
                                           DocumentType docType) {
        byte[] documentBytes = pdfBytes;
        if (docType != null && docType.equals(DocumentType.DOCX)) {
            documentBytes = docxBytes;
        }
        return documentBytes;
    }

    private String getCheckSum(byte[] pdfBuffer) {
        return new DigestUtils(HASH_ALGORITHM_NAME).digestAsHex(pdfBuffer);
    }

    private byte[] convertDocxToPdf(final byte[] docxBytes) {
        try {
            return formUtils.convertDocxToPdf(docxBytes, fontProvider);
        } catch (FileIOException exception) {
            log.error("Can not convert new docx to pdf", exception);
            throw new ConvertToPdfException("Can not convert new docx to pdf! ");
        }
    }

    private byte[] generateDocxFromTemplate(final FormGenerationRequest formData) {
        byte[] template = templateHandler.getTemplateByName("templates/" + formData.getForm().getPath());
        try {
            return yargUtils.generateDocxFromTemplate(template, formData);
        } catch (IOException exception) {
            log.error("Can not generate new docx from template", exception);
            throw new GeneratePrintedFormException("Can not generate new docx from template! " +
                    "From data: " + formData.getForm());
        }
    }

    private FormGenerationResponse buildFormGenerationResponse(final String formName,
                                                               final String checkSum,
                                                               final String pdfFileName) {
        return new FormGenerationResponse("link", formName, checkSum, pdfFileName);
    }

    private FormGenerationResponse buildFormGenerationResponse(final String formName,
                                                               final String checkSum,
                                                               final String pdfFileName,
                                                               final DocumentResponse documentResponse) {
        return new FormGenerationResponse(documentResponse.getLink(), formName, checkSum, pdfFileName);
    }
}
