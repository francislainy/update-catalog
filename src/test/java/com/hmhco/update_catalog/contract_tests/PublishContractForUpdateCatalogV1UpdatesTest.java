package com.hmhco.update_catalog.contract_tests;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ExtendWith(PactConsumerTestExt.class)
public class PublishContractForUpdateCatalogV1UpdatesTest {


    Map<String, String> headers = new HashMap<String, String>();

    String getRecommendations = "/toc/getsummary/ELA_NGL_G7_TX";

    @Pact(provider = "CRS-METADATA-FILTERING-SERVICE", consumer = "CRS-TOC-RECOMMENDER")
    public RequestResponsePact createPact(PactDslWithProvider builder) throws IOException {

        headers.put("Content-Type", "application/json");

        DslPart body = new PactDslJsonBody()
                .stringValue("programCode", "ELA_NGL_G7_TX")
                .eachLike("contentResources")
                .integerType("tocPosition", 0)
                .stringType("contentIdentifier", "l_9d23cb4f-69dc-4032-bb53-73501234dc14_e5f25016-e2fa-4223-8969-2004c644917d")
                .closeArray();

        return builder
                .given("get TOC Summary")
                .uponReceiving("get TOC Summary")
                .path(getRecommendations)
                .method("GET")
                .headers(headers)
                .willRespondWith()
                .status(200)
                .body(body)
                .toPact();
    }

    @Test
    @PactTestFor(providerName = "CRS-METADATA-FILTERING-SERVICE", port = "8080")
    public void runTest() {

        RestAssured.baseURI = "http://localhost:8080";

        Response getRecommendedResourcesMockResponse = RestAssured
                .given()
                .headers(headers)
                .when()
                .get(getRecommendations);

        assert (getRecommendedResourcesMockResponse.getStatusCode() == 200);
    }
}