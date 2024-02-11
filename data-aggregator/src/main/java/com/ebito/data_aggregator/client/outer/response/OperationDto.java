package com.ebito.data_aggregator.client.outer.response;

import com.ebito.payment.PaymentMethod;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class OperationDto {

    Long id;
    LocalDateTime timestamp;
    PaymentMethod paymentMethod;
    Long sum;
}
