package com.ebito.data_source.api.controller.response;

import com.ebito.currency.Currency;
import com.ebito.data_source.model.dto.OperationDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;

@Value
@Builder
public class DataResponse {

    @Schema(description = "id клиента",
            example = "1")
    Long clientId;

    @Schema(description = "Имя клиента",
            example = "John")
    String firstname;

    @Schema(description = "Фамилия клиента",
            example = "Smith")
    String lastname;

    @Schema(description = "Отчество клиента",
            example = "Doe")
    String middlename;

    @Schema(description = "Номер аккаунта клиента",
            example = "123456789")
    Integer accountNumber;

    @Schema(description = "Валюта счёта клиента",
            example = "USD")
    Currency accountCurrency;

    @Schema(description = "Список операций клиента",
            implementation = OperationDto.class)
    List<OperationDto> accountOperations;

    @Schema(description = "номер договора",
            example = "789")
    Integer agreementNumber;

    @Schema(description = "дата договора",
            example = "2022-01-01")
    LocalDate agreementDate;

}
