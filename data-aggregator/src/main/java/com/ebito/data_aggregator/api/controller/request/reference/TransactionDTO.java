package com.ebito.data_aggregator.api.controller.request.reference;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
@Getter
@Setter
public class TransactionDTO {

    private String id;
    private LocalDateTime dateTime;
    private String paymentMethod;
    private String sum;
}
