package com.ebito.data_source.model.dto;

import com.ebito.payment.PaymentMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class OperationDto {

     @Schema(description = "id аккаунта клиента",
             example = "123456789")
     Long id;

     @Schema(description = "время операции",
             example = "2022-01-01 10:00:00")
     LocalDateTime timestamp;

     @Schema(description = "id операции",
             example = "CARD")
     PaymentMethod paymentMethod;

     @Schema(description = "Денежная сумма",
             example = "100")
     Long sum;
}
