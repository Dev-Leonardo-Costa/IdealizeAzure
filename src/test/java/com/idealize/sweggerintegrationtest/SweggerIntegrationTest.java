package com.idealize.sweggerintegrationtest;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;

import com.idealize.config.IntegrationConfig;
import com.idealize.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SweggerIntegrationTest extends IntegrationConfig {

    @Test
    void testSweggerUiPage() {
       var content = given()
                .basePath("/swagger-ui/index.html")
                .port(TestConfig.SERVER_PORT)
                .when()
                    .get()
                .then()
                    .statusCode(200)
                .extract()
                    .body()
                            .asString();

       assertTrue(content.contains("Swagger UI"));
    }
}
