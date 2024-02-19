package com.ebito.orchestrator.api.controller;

import com.ebito.channel.Channel;
import com.ebito.orchestrator.api.OrchestratorApi;
import com.ebito.orchestrator.api.controller.request.PrintFormGenerationRequest;
import com.ebito.orchestrator.api.controller.response.PrintedGuids;
import com.ebito.orchestrator.client.data_aggregator.DataAggregatorClient;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static com.ebito.orchestrator.api.controller.Endpoints.CONTEXT_PATH;
import static com.ebito.orchestrator.api.controller.Endpoints.PrinterController.GENERATE_DOCUMENT;
import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


public class OrchestratorDataAggregatorClientApiTest extends AbstractApiTest {

    @MockBean
    OrchestratorApi orchestratorController;

    @MockBean
    private DataAggregatorClient dataAggregatorClient;
    String clientId = "1";

    PrintedGuids printedGuids = new PrintedGuids("https://rtkit.minio.ru/documents/somedocument.pdf",
            "Выписка по начислениям абонента",
            "7596c9e5bcb5dca0a6ea8a0704ad79ded2888950cfd077e2ff0d4962291acfc9",
            "001_created_01_01_1970_08_40_12.pdf");
    PrintFormGenerationRequest printFormGenerationRequest = new PrintFormGenerationRequest("reference001",
            "2",
            Channel.ONLINE,
            LocalDate.now().minusDays(10),
            LocalDate.now()
    );

    @BeforeEach
    void setUp() {

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setBasePath(CONTEXT_PATH)
                .setPort(port)
                .setContentType(ContentType.JSON)
                .build();

        ResponseEntity<PrintedGuids> expectedResponseEntity = new ResponseEntity<>(printedGuids, HttpStatus.OK);

        when(dataAggregatorClient.generateReference(clientId, printFormGenerationRequest)).thenReturn(expectedResponseEntity);
    }

    @Test
    @DisplayName("Given correct request " +
            "When call endpoint GET /clients/{clientId}/generate-document " +
            "Then return 200 http code")
    void testSuccessGenerateDocument() {

        given()
                .contentType(ContentType.JSON)
                .pathParam("clientId", clientId)
                .body(printFormGenerationRequest)
                .post(GENERATE_DOCUMENT)
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("Given server error request " +
            "When call endpoint GET /clients/{clientId}/generate-document " +
            "Then return 500 http code")
    void testServerErrorGenerateDocument() {
        when(orchestratorController.generateDocument(any(), any())).thenThrow(new RuntimeException());

        given()
                .contentType(ContentType.JSON)
                .pathParam("clientId", "RealId")
                .body(printFormGenerationRequest)
                .post(GENERATE_DOCUMENT)
                .then()
                .statusCode(500);
    }

}
