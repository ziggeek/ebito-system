package com.ebito.data_source.mapper;

import com.ebito.currency.Currency;
import com.ebito.data_source.model.entity.Account;
import com.ebito.data_source.model.entity.Agreement;
import com.ebito.data_source.model.entity.Client;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ClientMapperTest {

    @Test
    void toDto() {
        Client client = Client.builder()
                .id(1L)
                .lastname("Ivanov")
                .middlename("Ivanovich")
                .firstname("Ivan")
                .account(Account.builder()
                        .accountNumber(1212)
                        .accountCurrency(Currency.RUB)
                        .build())
                .agreement(Agreement.builder()
                        .agreementDate(LocalDate.now())
                        .agreementNumber(121232323)
                        .build())
                .build();

        var convert = ClientMapper.INSTANCE.toDto(client);

    }
}