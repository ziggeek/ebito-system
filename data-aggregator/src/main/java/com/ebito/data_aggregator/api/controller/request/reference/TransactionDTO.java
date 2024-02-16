package com.ebito.data_aggregator.api.controller.request.reference;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
@Data
public class TransactionDTO {

    private String id;
    private LocalDateTime dateTime;
    private String paymentMethod;
    private String sum;
}
