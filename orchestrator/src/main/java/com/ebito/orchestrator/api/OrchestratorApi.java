package com.ebito.orchestrator.api;

import com.ebito.exceptionhandler.model.ErrorResponse;
import com.ebito.orchestrator.api.controller.request.PrintFormGenerationRequest;
import com.ebito.orchestrator.api.controller.response.ClientDocuments;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Tag(name = "Orchestrator", description = "Orchestrator API")
@RequestMapping("/api/v1")
public interface OrchestratorApi {


    @GetMapping("/{clientId}/get-client-documents")
    @Operation(
            summary = "Получение всех сгенерированных справок клиента",
            description = "Позволяет получить список ссылок на справки по id клиента"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Список справок получен",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ClientDocuments.class))),
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
    ResponseEntity<ClientDocuments> getClientDocuments(
            @PathVariable("clientId") @Parameter(description = "Идентификатор клиента") String clientId
    );


    @PostMapping("/clients/{clientId}/generate-document")
    @Operation(
            summary = "Получение определенного тип справки клиента",
            description = "Позволяет получить справку по заданным типу справки и id клиента"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Запрос на генерацию документа отправлен"),
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
    ResponseEntity<Void> generateDocument(
            @PathVariable("clientId") @Parameter(description = "Идентификатор  клиента") String clientId,
            @Valid @RequestBody @Parameter(description = "Код формируемого документа в запросе") PrintFormGenerationRequest request
    );
}
