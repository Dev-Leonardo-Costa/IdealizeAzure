package com.idealize.integradioncontrollers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idealize.config.IntegrationConfig;
import com.idealize.config.TestConfig;
import com.idealize.models.Person;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RestTest extends IntegrationConfig {

    private static RequestSpecification specification;
    private static ObjectMapper mapper;
    private static Person person;

    @BeforeAll
    public static void setup() {
        // Given
        mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        specification = new RequestSpecBuilder()
                .setBasePath("/person")
                .setPort(TestConfig.SERVER_PORT)
                    .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                    .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        person = new Person("Leonardo",
                "Costa",
                "leo4921@gmail.com",
                "Rua major celestino 1045 - Ant-Bezerra",
                "male");
    }

    @Test
    @Order(1)
    @DisplayName("Test de integração para o created")
    void integrationTestCreate() throws JsonProcessingException {
        var content = RestAssured.given().spec(specification)
                .contentType(TestConfig.CONTENT_TYPE_JSON)
                .body(person)
                .when()
                    .post()
                .then()
                    .statusCode(201)
                        .extract()
                        .body()
                        .asString();

        Person creadtPerson = mapper.readValue(content, Person.class);
        person = creadtPerson;

        assertNotNull(creadtPerson.getId());
        assertNotNull(creadtPerson.getFirstName());
        assertNotNull(creadtPerson.getLastName());
        assertNotNull(creadtPerson.getGender());
        assertNotNull(creadtPerson.getAddress());
        assertNotNull(creadtPerson.getEmail());

        assertTrue(creadtPerson.getId() > 0);
        assertEquals("Leonardo",creadtPerson.getFirstName());
        assertEquals("Costa", creadtPerson.getLastName());
        assertEquals("leo4921@gmail.com", creadtPerson.getEmail());
        assertEquals("Rua major celestino 1045 - Ant-Bezerra", creadtPerson.getAddress());
        assertEquals("male", creadtPerson.getGender());

    }

}
