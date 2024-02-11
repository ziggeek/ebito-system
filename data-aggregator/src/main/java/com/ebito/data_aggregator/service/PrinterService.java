package com.ebito.data_aggregator.service;

import com.ebito.data_aggregator.api.controller.request.PrintData;
import com.ebito.data_aggregator.client.local.PrinterClient;
import com.ebito.data_aggregator.api.controller.response.PrintedGuids;
import com.ebito.document.DocumentType;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrinterService {

    private final PrinterClient printerClient;

    public PrintedGuids getPrint(PrintData printData) {

        try {
            PrintedGuids printed = this.sendRequest(printData);
            log.info("*** Пришел ответ от сервиса печатных форм: {}", printed);
            return printed;
        } catch (FeignException ex) {
            log.error("*** Сервис печатных форм вернул ошибку: ", ex);
            throw new RuntimeException(ex);
        }
    }

    private PrintedGuids sendRequest(PrintData printData) {
        log.info("*** Отправляем запрос в сервис печатных форм: {}", printData);
        return printerClient.generatePrintForm( DocumentType.PDF, printData.getClientId(), printData).getBody();
    }
}
