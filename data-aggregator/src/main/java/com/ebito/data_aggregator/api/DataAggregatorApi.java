package com.ebito.data_aggregator.api;

import com.ebito.data_aggregator.rabbitmq.model.PrintFormGenerationRequest;
import com.ebito.data_aggregator.api.controller.response.PrintedGuids;
import com.ebito.exceptionhandler.model.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Tag(name = "Data Aggregator", description = "Data Aggregator API")
@RequestMapping("/api/v1")
public interface DataAggregatorApi {

    @Operation(summary = "Сгенерировать форму определенного типа для клиента")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Данные сгенерированной формы получены",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PrintedGuids.class))),
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
    @PostMapping("/clients/{clientId}/generate-print-form")
    ResponseEntity<PrintedGuids> generatePrintForm(@PathVariable("clientId") String clientId,
                                                   @Valid @RequestBody PrintFormGenerationRequest request);
}
