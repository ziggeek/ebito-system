package com.ebito.orchestrator.api.controller;

import com.ebito.orchestrator.api.OrchestratorApi;
import com.ebito.orchestrator.api.controller.response.ClientDocument;
import com.ebito.orchestrator.api.controller.response.ClientDocuments;
import com.ebito.orchestrator.client.cloud.CloudClient;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static com.ebito.orchestrator.api.controller.Endpoints.CONTEXT_PATH;
import static com.ebito.orchestrator.api.controller.Endpoints.PrinterController.GET_DOCUMENTS;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;


public class OrchestratorCloudClientApiTest extends AbstractApiTest {

    @MockBean
    private CloudClient cloudClient;

    String clientId = "1";
    ClientDocument clientDocument = ClientDocument.builder()
            .name("Выписка по начислениям абонента")
            .link("https://rtkit.minio.ru/documents/somedocument.pdf")
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

        List<ClientDocument> documents = new ArrayList<>();
        documents.add(clientDocument);

        ClientDocuments clientDocuments = new ClientDocuments(documents);

        ResponseEntity<ClientDocuments> expectedResponseEntity = new ResponseEntity<>(clientDocuments, HttpStatus.OK);


        when(cloudClient.getClientDocuments(clientId)).thenReturn(expectedResponseEntity);


        when(cloudClient.getClientDocuments("RealId")).thenThrow(
                new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ошибка сервера")
        );
    }

    @Test
    @DisplayName("Given correct request " +
            "When call endpoint GET /{clientId}/get-client-documents " +
            "Then return 200 http code")
    void testSuccessGetClientDocuments() {

        given()
                .get(GET_DOCUMENTS, clientId)
                .then()
                .statusCode(200)
                .body("documents[0].name", notNullValue())
                .body("documents[0].link", notNullValue());
    }


    @Test
    @DisplayName("Given server error request " +
            "When call endpoint GET /{clientId}/get-client-documents " +
            "Then return 500 http code")
    void testServerErrorGetClientDocuments() {

        given()
                .get(GET_DOCUMENTS, "RealId")
                .then()
                .statusCode(500);
    }

}
