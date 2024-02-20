package com.ebito.data_aggregator.service;


import com.ebito.data_aggregator.api.controller.request.PrintData;
import com.ebito.data_aggregator.api.controller.request.reference.Reference001PrintData;
import com.ebito.data_aggregator.api.controller.response.PrintedGuids;
import com.ebito.data_aggregator.client.outer.DataSourceClient;
import com.ebito.data_aggregator.client.outer.response.DataResponse;
import com.ebito.data_aggregator.client.outer.response.OperationDto;
import com.ebito.data_aggregator.mapper.TransactionMapper;
import com.ebito.data_aggregator.api.controller.request.reference.TransactionDTO;
import com.ebito.data_aggregator.rabbitmq.model.PrintFormGenerationRequest;
import com.ebito.exceptionhandler.exception.IncorrectDocumentTypeException;
import com.ebito.exceptionhandler.exception.ResourceNotFoundException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonService {

    private static final String PATH_REFERENCE = "com.ebito.data_aggregator.service.reference.reference%s.Reference%sService";
    private static final String PATH_AGREEMENT = "com.ebito.data_aggregator.service.agreement.agreement%s.Agreement%sService";

    private final ApplicationContext context;
    private final DataSourceClient dataSourceClient;

    public PrintedGuids selectPrintForm(final PrintFormGenerationRequest request) {
        Class<?> dogClass;
        try {
            dogClass = getDogClass(request);
        } catch (IncorrectDocumentTypeException e) {
            log.error(e.getMessage());
            throw new ResourceNotFoundException(e.getMessage());
        }
        PrintData printData = this.buildPrintData(request);
        PrintedGuids printedGuids = ((Reference000Service) context.getBean(dogClass)).execute(printData);
        return printedGuids;
    }

    private static Class<?> getDogClass(PrintFormGenerationRequest request) {
        try {
            if (request.getDocumentCode().contains("reference")) {
                String code = request.getDocumentCode().substring(9);
                return Class.forName(String.format(PATH_REFERENCE, code, code));
            } else if (request.getDocumentCode().contains("agreement")) {
                String code = request.getDocumentCode().substring(9);
                return Class.forName(String.format(PATH_AGREEMENT, code, code));
            } else {
                throw new ClassNotFoundException();
            }
        } catch (ClassNotFoundException e) {
            throw new IncorrectDocumentTypeException(String.format("Заданный тип документа не найден: %s", request.getDocumentCode()));
        }
    }

    private PrintData buildPrintData(PrintFormGenerationRequest request) {
        Assert.notNull(request, "'request' не должен быть null");

        if (Objects.equals("reference001", request.getDocumentCode())) {

            DataResponse data;
            try {
                data = dataSourceClient.getData(request.getClientId()).getBody();
            } catch (FeignException ex) {
                if (ex.status() == 404) {
                    throw new ResourceNotFoundException(String.format("Client with id=%s not found", request.getClientId()));
                } else {
                    throw ex;
                }
            }

            List<OperationDto> selectedClientOperations = data.getAccountOperations().stream()
                    .filter(ao -> ao.getTimestamp().isAfter(request.getDateFrom().atStartOfDay()) && ao.getTimestamp().isBefore(request.getDateTo().atStartOfDay())
            ).collect(Collectors.toList());

            List<TransactionDTO> clientTransactions = TransactionMapper.INSTANCE.toListDto(selectedClientOperations);

            Long totalAmount = data.getAccountOperations().stream()
                    .map(OperationDto::getSum)
                    .mapToLong(Long::longValue)
                    .sum();

            return Reference001PrintData.builder()
                    .form(request.getDocumentCode())
                    .clientId(Long.parseLong(request.getClientId()))
                    .dateFrom(request.getDateFrom())
                    .dateTo(request.getDateTo())
                    .lastName(data.getLastname())
                    .firstName(data.getFirstname())
                    .middleName(data.getMiddlename())
                    .agreementNumber(data.getAgreementNumber())
                    .dateSigningAgreement(data.getAgreementDate())
                    .accountNumber(String.valueOf(data.getAccountNumber()))
                    .accountCurrency(data.getAccountCurrency())
                    .totalAmount(totalAmount)
                    .transactions(clientTransactions)
                    .channel(request.getChannel())
                    .build();

        } else if (Objects.equals("reference002", request.getDocumentCode())) {
            log.info("*** GOT DATA FROM DATA-SOURCE FOR REFERENCE 002");
            return null;

        } else if (Objects.equals("agreement001", request.getDocumentCode())) {
            log.info("*** GOT DATA FROM DATA-SOURCE FOR AGREEMENT 001");
            return null;

        } else {
            String message = String.format("Некорректный тип документа: %s", request.getDocumentCode());
            log.error(message);
            throw new IncorrectDocumentTypeException(message);
        }
    }
}
