package com.ebito.data_source.api.controller;

import com.ebito.currency.Currency;
import com.ebito.data_source.api.controller.response.DataResponse;
import com.ebito.data_source.service.impl.DataServiceImpl;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Collections;

import static com.ebito.data_source.api.controller.Endpoints.CONTEXT_PATH;
import static com.ebito.data_source.api.controller.Endpoints.PrinterController.GET_DATA;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;


public class DataSourceApiTest extends AbstractApiTest {

    @MockBean
    private DataServiceImpl dataService;
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

    @BeforeEach
    void setUp() {

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setBasePath(CONTEXT_PATH)
                .setPort(port)
                .setContentType(ContentType.JSON)
                .build();

        when(dataService.getData(clientId)).thenReturn(expectedResponse);

        when(dataService.getData("WrongId")).thenThrow(
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Клиент не найден")
        );

        when(dataService.getData("RealId")).thenCallRealMethod();
    }

    @Test
    @DisplayName("Given correct request " +
            "When call endpoint POST /forms/generate-print-form " +
            "Then return 200 http code")
    void testSuccessGetData() {

        given()
                .get(GET_DATA, clientId)
                .then()
                .statusCode(200)
                .body("firstname", notNullValue())
                .body("middlename", notNullValue())
                .body("accountNumber", notNullValue())
                .body("accountCurrency", notNullValue())
                .body("accountOperations", notNullValue())
                .body("accountCurrency", notNullValue())
                .body("agreementNumber", notNullValue())
                .body("agreementDate", notNullValue());
    }

    @Test
    @DisplayName("Not found request " +
            "When call endpoint POST /forms/generate-print-form " +
            "Then return 404 http code")
    void testBadRequestGetData() {

        given()
                .get(GET_DATA, "WrongId")
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("Given server error request " +
            "When call endpoint POST /forms/generate-print-form " +
            "Then return 500 http code")
    void testServerErrorGetData() {

        given()
                .get(GET_DATA, "RealId")
                .then()
                .statusCode(500);
    }

}
