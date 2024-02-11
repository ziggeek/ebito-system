package com.ebito.reference;

import com.ebito.data_aggregator.DataAggregatorApplication;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@Sql(scripts = {}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "")
@ActiveProfiles("h2")
@SpringBootTest(classes = DataAggregatorApplication.class)
public abstract class IntegrationTestBaseWithH2Database {

    @BeforeAll
    static void init() {
    }

    @AfterAll
    static void afterAll() {
    }
}

