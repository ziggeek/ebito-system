package com.ebito.cloud.api;

import com.ebito.cloud.model.response.DocumentResponse;
import com.ebito.cloud.model.response.ListDocumentResponse;
import com.ebito.exceptionhandler.model.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Cloud", description = "Cloud API сервис для хранения и получения документов")
@RequestMapping("/api/v1")
public interface CloudApi {

    @Operation(summary = "Сохранить файл и отправить ответ сгенерированной справки")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Документ сохранен",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DocumentResponse.class))),
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
    @PostMapping(value = "/{clientId}/documents/save-client-document", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<DocumentResponse> saveClientDocument(
            @RequestPart(name = "file") @Parameter(description = "Файл для загрузки", required = true) MultipartFile file,
            @PathVariable("clientId") @Parameter(description = "id клиента", required = true) String clientId,
            @RequestParam(name = "checksum") @Parameter(description = "чексумма документа", required = true) String checksum

    );

    @Operation(summary = "Получить список документов клиента")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Документ сохранен",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ListDocumentResponse.class))),
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
    @GetMapping("/{clientId}/get-client-documents")
    ResponseEntity<ListDocumentResponse> getClientDocuments(@PathVariable("clientId") @Parameter(description = "id клиента", required = true) String clientId);

    @Operation(summary = "Получить ссылку на документ")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Документ сохранен",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = String.class))),
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
    @GetMapping(value = "/get-document-link/{name}")
    ResponseEntity<String> getDocumentLinkByName(@PathVariable("name") @Parameter(description = "Имя документа", required = true) String name);


}
