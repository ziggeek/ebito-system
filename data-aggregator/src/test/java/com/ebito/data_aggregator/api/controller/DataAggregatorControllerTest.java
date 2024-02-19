package com.ebito.data_aggregator.api.controller;

import com.ebito.channel.Channel;
import com.ebito.data_aggregator.AbstractControllerTest;
import com.ebito.data_aggregator.api.controller.response.PrintedGuids;
import com.ebito.data_aggregator.rabbitmq.model.PrintFormGenerationRequest;
import com.ebito.data_aggregator.service.CommonService;
import com.ebito.exceptionhandler.exception.ResourceNotFoundException;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static com.ebito.data_aggregator.api.EndPoints.CONTEXT_PATH;
import static com.ebito.data_aggregator.api.EndPoints.DataAggregatorController.GENERATE_PRINT_FORM;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;

public class DataAggregatorControllerTest extends AbstractControllerTest {

    private static final PrintFormGenerationRequest CORRECT_REQUEST = new PrintFormGenerationRequest(
            "reference001", "1", Channel.ONLINE, LocalDate.of(2000, 3, 15), LocalDate.of(2024, 3, 15)
    );

    private static final PrintFormGenerationRequest REQUEST_WITH_INCORRECT_DOCUMENT_TYPE = new PrintFormGenerationRequest(
            "testIncorrectValue", "1", Channel.ONLINE, LocalDate.of(2000, 3, 15), LocalDate.of(2024, 3, 15)
    );

    private static final PrintFormGenerationRequest CORRECT_REQUEST_FOR_MICROSERVICE_OFF_CASE = new PrintFormGenerationRequest(
            "reference001", "111", Channel.BRANCH, LocalDate.of(2010, 3, 15), LocalDate.of(2024, 3, 15)
    );

    private static final PrintedGuids RESPONSE = new PrintedGuids("https://rtkit.minio.ru/documents/somedocument.pdf", "Выписка по начислениям абонента",
            "7596c9e5bcb5dca0a6ea8a0704ad79ded2888950cfd077e2ff0d4962291acfc9", "created_25_12_2020_08_47_50_jFEH2f.pdf"
    );


    @MockBean
    private CommonService mockCommonService;

    @BeforeEach
    void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setBasePath(CONTEXT_PATH)
                .setPort(port)
                .setContentType(ContentType.JSON)
                .build();

        when(mockCommonService.selectPrintForm(CORRECT_REQUEST)).thenReturn(RESPONSE);
        when(mockCommonService.selectPrintForm(REQUEST_WITH_INCORRECT_DOCUMENT_TYPE)).thenThrow(
                new ResourceNotFoundException("Заданный тип документа не найден: testIncorrectValue")
        );
        when(mockCommonService.selectPrintForm(CORRECT_REQUEST_FOR_MICROSERVICE_OFF_CASE)).thenCallRealMethod();
    }

    @Test
    @DisplayName("Should return 200 when request is correct")
    void givenCorrectRequest_whenCallEndpoint_thenReturn200Created() {
        given()
                .contentType(ContentType.JSON)
                .body(CORRECT_REQUEST)
                .post(String.format(GENERATE_PRINT_FORM, 1))
                .then()
                .statusCode(200)
                .body("link", notNullValue())
                .body("name", notNullValue())
                .body("checkSum", notNullValue())
                .body("pdfFileName", notNullValue());
    }

    @Test
    @DisplayName("Should return 404 when document type was not found")
    void givenIncorrectRequest_whenCallEndpoint_thenReturn404NotFound() {
        given()
                .body(REQUEST_WITH_INCORRECT_DOCUMENT_TYPE)
                .post(String.format(GENERATE_PRINT_FORM, 1))
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("Should return 500 when data source microservice is off")
    void shouldReturn500WhenDataSourceMicroserviceOff() {
        given()
                .body(CORRECT_REQUEST_FOR_MICROSERVICE_OFF_CASE)
                .post(String.format(GENERATE_PRINT_FORM, 111))
                .then()
                .statusCode(500);
    }
}
