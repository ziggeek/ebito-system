package com.ebito.data_aggregator.client.outer.response;

import com.ebito.currency.Currency;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;

/**
 * Ответ сервиса data-source
 */
@Value
@Builder
public class DataResponse {

    Long clientId;
    String firstname;
    String lastname;
    String middlename;
    Integer accountNumber;
    Currency accountCurrency;
    List<OperationDto> accountOperations;
    Integer agreementNumber;
    LocalDate agreementDate;
}
