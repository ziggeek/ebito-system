package com.ebito.data_source.service;

import com.ebito.currency.Currency;
import com.ebito.data_source.api.controller.response.DataResponse;
import com.ebito.data_source.model.entity.Account;
import com.ebito.data_source.model.entity.Agreement;
import com.ebito.data_source.model.entity.Client;
import com.ebito.data_source.repository.ClientRepository;
import com.ebito.data_source.service.impl.DataServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DataServiceTest {

    @InjectMocks
    private DataServiceImpl dataService;
    @Mock
    private ClientRepository clientRepository;


    @Test
    @DisplayName("Получение данных клиента по id")
    void testGetData() {
        String clientId = "1";

        DataResponse expectedResponse = DataResponse.builder()
                .clientId(Long.valueOf(clientId))
                .firstname("John")
                .lastname("Doe")
                .middlename("Smith")
                .accountNumber(123456789)
                .accountCurrency(Currency.USD)
                .accountOperations(Collections.emptyList())
                .agreementNumber(789)
                .agreementDate(LocalDate.parse("2022-01-01"))
                .build();

        Client client = Client.builder()
                .id(Long.valueOf(clientId))
                .firstname("John")
                .lastname("Doe")
                .middlename("Smith")
                .account(Account.builder()
                        .accountNumber(123456789)
                        .accountCurrency(Currency.USD)
                        .operations(Collections.emptyList())
                        .build())
                .agreement(Agreement.builder()
                        .agreementNumber(789)
                        .agreementDate(LocalDate.parse("2022-01-01"))
                        .build())
                .build();

        when(clientRepository.findById(Long.valueOf(clientId))).thenReturn(Optional.of(client));

        DataResponse dataResponse = dataService.getData(clientId);

        Assertions.assertEquals(expectedResponse, dataResponse);
    }
}
