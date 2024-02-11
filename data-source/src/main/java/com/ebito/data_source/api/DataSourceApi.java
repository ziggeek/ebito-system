package com.ebito.data_source.api;


import com.ebito.data_source.api.controller.response.DataResponse;
import com.ebito.exceptionhandler.model.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Data Source", description = "Data Source API")
@RequestMapping("/api/v1")
public interface DataSourceApi {

    @Operation(
            summary = "Получение данных клиента по id",
            description = "Позволяет получить необходимую информацию для создания справки по id клиента"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Данные получены",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DataResponse.class))),
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
    @GetMapping("/clients/{clientId}/get-data")
    ResponseEntity<DataResponse> getData(@PathVariable String clientId);
}
