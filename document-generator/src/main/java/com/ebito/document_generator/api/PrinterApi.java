package com.ebito.document_generator.api;


import com.ebito.document_generator.api.controller.response.FormGenerationResponse;
import com.ebito.document_generator.api.controller.request.FormGenerationRequest;
//import com.ebito.document_generator.model.DocumentType;
import com.ebito.document.DocumentType;
import com.ebito.exceptionhandler.model.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.io.IOException;

@Tag(name = "PrintedForm", description = "PrintedForm API")
@RequestMapping("/api/v1")
public interface PrinterApi {

    @Operation(summary = "Сгенерировать форму определенного типа")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Форма сгенерирована",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FormGenerationResponse.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "В случае нарущения контракта",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "В случае внутренних ошибок",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping("/forms/generate-print-form")
    ResponseEntity<FormGenerationResponse> generatePrintForm(
            @RequestParam(name = "docType", required = false) DocumentType docType,
            @RequestParam(name = "clientId") String clientId,
            @RequestBody @Valid FormGenerationRequest event
    ) throws IOException;
}
