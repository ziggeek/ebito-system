package com.ebito.document_generator.api.controller.request.reference;

import com.ebito.channel.Channel;
import com.ebito.currency.Currency;
import com.ebito.document_generator.api.controller.request.FormGenerationRequest;
import com.ebito.document_generator.model.Form;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.lang.Nullable;

import javax.validation.ValidationException;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.ebito.document_generator.util.DataFormatUtils.*;

/**
 * Данные для заполнения "Выписка по начислениям абонента"
 */
@Value
@EqualsAndHashCode(callSuper = false)
@Builder
public class Reference001FormGenerationRequest implements FormGenerationRequest {

    @Schema(description = "Дата C",
            example = "2021-03-15")
    @NotNull(message = "must not be null")
    LocalDate dateFrom;

    @Schema(description = "Дата ПО",
            example = "2023-03-15")
    @NotNull(message = "must not be null")
    LocalDate dateTo;

    @Schema(description = "Фамилия",
            example = "Ivanov")
    @NotBlank(message = "must not be blank")
    @Size(max = 50, message = "must contain less than 50")
    String lastName;

    @Schema(description = "Имя",
            example = "Ivan")
    @NotBlank(message = "must not be blank")
    @Size(max = 50, message = "must contain less than 50")
    String firstName;

    @Schema(description = "Отчество",
            example = "Ivanovich")
    @Nullable
    @Size(max = 50, message = "must contain less than 50 letters")
    String middleName;

    @Schema(description = "Номер договора",
            example = "123456")
    @NotBlank(message = "must not be blank")
    @Size(max = 6, message = "must contain less than 6")
    String agreementNumber;

    @Schema(description = "Дата заключения договора",
            example = "2010-03-15")
    @NotNull(message = "must not be null")
    LocalDate dateSigningAgreement;

    @Schema(description = "Номер счета",
            example = "12345678901234567890")
    @NotBlank(message = "must not be blank")
    @Size(max = 25, message = "must contain less than 25")
    String accountNumber;

    @NotNull(message = "must not be null")
    @Schema(description = "Валюта",
            allowableValues = {"RUB", "USD", "EURO"})
    Currency accountCurrency;

    @NotNull(message = "must not be null")
    @Schema(description = "Канал запроса на генерацию документа",
            allowableValues = {"BRANCH", "ONLINE", "MOBILE"})
    Channel channel;

    @Schema(description = "Сумма поступлений за период dateFrom-dateTo",
            example = "1295.01")
    @NotBlank(message = "must not be blank")
    @Size(max = 50, message = "must contain less than 50")
    String totalAmount;

    @Schema(description = "Транзакции начисления абонента", example = "")
    @NotNull(message = "must not be null")
    List<Transaction> transactions;

    @Data
    @Builder
    public static class Transaction {

        @Schema(description = "Идентификатор транзакции", example = "12345678", required = true)
        @NotBlank(message = "must not be blank")
        private final String id;

        @Schema(description = "Дата и время совершения транзакции",
                example = "2021-03-15T12:30:00", required = true)
        @NotNull(message = "must not be null")
        private final LocalDateTime dateTime;

        @Schema(description = "Способ пополнения", example = "СБП", required = true)
        @NotBlank(message = "must not be blank")
        private final String paymentMethod;

        @Schema(description = "Сумма пополнения", example = "1000.01", required = true)
        @NotBlank(message = "must not be blank")
        private final String sum;

        public String getId() {
            return this.id;
        }

        public String getDateTime() {
            return getDateTimeInRightFormat(this.dateTime);
        }

        public String getPaymentMethod() {
            return this.paymentMethod;
        }

        public String getSum() {
            return this.sum;
        }

    }

    public String getDateFrom() {
        return getDateInRightFormat(this.dateFrom);
    }

    public String getDateTo() {
        return getDateInRightFormat(this.dateTo);
    }

    public String getClientFIO() {
        return getFullName(this.lastName, this.firstName, this.middleName);
    }

    public String getAgreementNumber() {
        return this.agreementNumber;
    }

    public String getDateSigningAgreement() {
        return getDateInRightFormat(this.dateSigningAgreement);
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public String getAccountCurrency() {
        return this.accountCurrency.getCurrencyFullName();
    }

    public String getChannel() {
        return this.channel.getChannelNameForForm();
    }

    public String getTotalAmount() {
        return this.totalAmount;
    }

    public List<Transaction> getTransactions() {
        return this.transactions;
    }

    @Override
    public Form getForm() {
        switch (channel) {
            case BRANCH:
                return Form.REFERENCE_001_BRANCH;
            case ONLINE:
                return Form.REFERENCE_001_ONLINE;
            case MOBILE:
                return Form.REFERENCE_001_MOBILE;
            default:
                throw new ValidationException("Not found appropriate printed form for channel: " + channel.name());
        }
    }
}