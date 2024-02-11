package com.ebito.document_generator.api.controller;

import com.ebito.document_generator.api.PrinterApi;
import com.ebito.document_generator.api.controller.request.FormGenerationRequest;
import com.ebito.document_generator.api.controller.response.FormGenerationResponse;
//import com.ebito.document_generator.model.DocumentType;
import com.ebito.document.DocumentType;
import com.ebito.document_generator.service.FormGeneratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PrinterController implements PrinterApi {

    private final FormGeneratorService formGeneratorService;

    @Override
    public ResponseEntity<FormGenerationResponse> generatePrintForm(
            final DocumentType docType,
            final String clientId,
            final FormGenerationRequest request
    ) throws IOException {
        FormGenerationResponse body = formGeneratorService.generate(clientId, docType, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }
}
