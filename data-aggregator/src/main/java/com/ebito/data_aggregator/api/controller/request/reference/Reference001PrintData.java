package com.ebito.data_aggregator.api.controller.request.reference;

import com.ebito.currency.Currency;
import com.ebito.data_aggregator.api.controller.request.PrintData;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

/**
 * Данные для заполнения Выписки по начислениям абонента
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Reference001PrintData extends PrintData {
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String lastName;
    private String firstName;
    private String middleName;
    private Integer agreementNumber;
    private LocalDate dateSigningAgreement;
    private String accountNumber;
    private Currency accountCurrency;
    private Long totalAmount;
    private List<TransactionDTO> transactions;
}
