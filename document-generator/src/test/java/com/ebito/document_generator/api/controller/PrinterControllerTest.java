package com.ebito.document_generator.api.controller;

import com.ebito.document_generator.AbstractTest;
import com.ebito.document_generator.api.controller.response.FormGenerationResponse;
import com.ebito.document_generator.service.FormGeneratorService;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;

import static com.ebito.document_generator.api.EndPoints.CONTEXT_PATH;
import static com.ebito.document_generator.api.EndPoints.PrinterController.GENERATE_PRINT_FORM;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PrinterControllerTest extends AbstractTest {


    @MockBean
    private FormGeneratorService formGeneratorService;

    @BeforeEach
    void setUp() throws IOException {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setBasePath(CONTEXT_PATH)
                .setPort(port)
                .setContentType(ContentType.JSON)
                .build();

        var response = FormGenerationResponse.builder()
                .link("https://rtkit.minio.ru/documents/somedocument.pdf")
                .name("Выписка по начислениям абонента")
                .checkSum("7596c9e5bcb5dca0a6ea8a0704ad79ded2888950cfd077e2ff0d4962291acfc9")
                .pdfFileName("created_25_12_2020_08_47_50_jFEH2f.pdf")
                .build();

        when(formGeneratorService.generate(any(), any(), any())).thenReturn(response);
    }

    @Test
    @DisplayName("Given correct request " +
            "When call endpoint POST /forms/generate-print-form " +
            "Then return 201 http code")
    void checkSuccessCallEndpoint() {
//        Reference001FormGenerationRequest request = Reference001FormGenerationRequest.builder()
//                .dateFrom("2021-03-15")
//                .dateTo()
//                .lastName()
//                .firstName()
//                .middleName()
//                .agreementNumber()
//                .dateSigningAgreement()
//                .accountNumber()
//                .accountCurrency()
//                .channel()
//                .totalAmount()
//                .transactions(Transaction.builder()
//                        .id()
//                        .dateTime()
//                        .paymentMethod()
//                        .sum()
//                        .build())
//                .build();

        String request = "{\n" +
                "  \"form\": \"reference001\",\n" +
                "  \"dateFrom\": \"2021-03-15\",\n" +
                "  \"dateTo\": \"2023-03-15\",\n" +
                "  \"lastName\": \"Ivanov\",\n" +
                "  \"firstName\": \"Ivan\",\n" +
                "  \"middleName\": \"Ivanovich\",\n" +
                "  \"agreementNumber\": \"123456\",\n" +
                "  \"dateSigningAgreement\": \"2010-03-15\",\n" +
                "  \"accountNumber\": \"12345678901234567890\",\n" +
                "  \"accountCurrency\": \"RUB\",\n" +
                "  \"channel\": \"BRANCH\",\n" +
                "  \"totalAmount\": \"1295.01\",\n" +
                "  \"transactions\": [\n" +
                "    {\n" +
                "      \"id\": \"12345678\",\n" +
                "      \"dateTime\": \"2021-03-15T12:30:00\",\n" +
                "      \"paymentMethod\": \"СБП\",\n" +
                "      \"sum\": \"1000.01\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        given()
                .queryParam("clientId", "1")
                .body(request)
                .post(GENERATE_PRINT_FORM)
                .then()
                .statusCode(201)
                .body("link", notNullValue())
                .body("name", notNullValue())
                .body("checkSum", notNullValue())
                .body("pdfFileName", notNullValue());
    }
}