package com.idealize.integradioncontrollers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idealize.config.IntegrationConfig;
import com.idealize.config.TestConfig;
import com.idealize.models.Person;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
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
        var content = given().spec(specification)
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
        assertEquals("Leonardo", creadtPerson.getFirstName());
        assertEquals("Costa", creadtPerson.getLastName());
        assertEquals("leo4921@gmail.com", creadtPerson.getEmail());
        assertEquals("Rua major celestino 1045 - Ant-Bezerra", creadtPerson.getAddress());
        assertEquals("male", creadtPerson.getGender());

    }

    @Test
    @Order(2)
    @DisplayName("Test de integração para atualizar um person")
    void integrationTestUpdate() throws JsonProcessingException {

        person.setFirstName("Leandro");
        person.setEmail("leandro@gmail.com.br");

        var content = given().spec(specification)
                .contentType(TestConfig.CONTENT_TYPE_JSON)
                .body(person)
                .when()
                .put()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        Person updatePerson = mapper.readValue(content, Person.class);
        person = updatePerson;

        assertNotNull(updatePerson.getId());
        assertNotNull(updatePerson.getFirstName());
        assertNotNull(updatePerson.getLastName());
        assertNotNull(updatePerson.getGender());
        assertNotNull(updatePerson.getAddress());
        assertNotNull(updatePerson.getEmail());

        assertTrue(updatePerson.getId() > 0);
        assertEquals("Leandro", updatePerson.getFirstName());
        assertEquals("leandro@gmail.com.br", updatePerson.getEmail());

    }

    @Test
    @Order(3)
    @DisplayName("Test de integração para buscra por id um person")
    void integrationTestFindByIdPersonObj() throws JsonProcessingException {

        var content = given().spec(specification)
                .pathParam("id", person.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        Person findByPerson = mapper.readValue(content, Person.class);

        assertNotNull(findByPerson.getId());
        assertNotNull(findByPerson.getFirstName());
        assertNotNull(findByPerson.getLastName());
        assertNotNull(findByPerson.getGender());
        assertNotNull(findByPerson.getAddress());
        assertNotNull(findByPerson.getEmail());

        assertTrue(findByPerson.getId() > 0);
        assertEquals("Leandro", findByPerson.getFirstName());
        assertEquals("leandro@gmail.com.br", findByPerson.getEmail());

    }

    @Test
    @Order(4)
    @DisplayName("Test de integração para listar person")
    void integrationTestPersonList() throws JsonProcessingException {

        given().spec(specification)
                .contentType(TestConfig.CONTENT_TYPE_JSON)
                .body(new Person(
                        "Elisane",
                        "Moraes",
                        "elis@gmail.com",
                        "Fortaleza-ce",
                        "Female"))
                .when()
                .post()
                .then()
                .statusCode(201);

        var content = given().spec(specification)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        List<Person> listPerson = Arrays.asList(mapper.readValue(content, Person[].class));

        Person elisane = listPerson.get(0);

        assertNotNull(elisane.getId());
        assertNotNull(elisane.getFirstName());
        assertNotNull(elisane.getLastName());
        assertNotNull(elisane.getGender());
        assertNotNull(elisane.getAddress());
        assertNotNull(elisane.getEmail());

        assertTrue(elisane.getId() > 0);
        assertEquals("Leandro", elisane.getFirstName());
        assertEquals("leandro@gmail.com.br", elisane.getEmail());

        Person elisafe = listPerson.get(1);

        assertNotNull(elisafe.getId());
        assertNotNull(elisafe.getFirstName());
        assertNotNull(elisafe.getLastName());
        assertNotNull(elisafe.getGender());
        assertNotNull(elisafe.getAddress());
        assertNotNull(elisafe.getEmail());

        assertTrue(elisafe.getId() > 0);
        assertEquals("Elisane", elisafe.getFirstName());
        assertEquals("elis@gmail.com", elisafe.getEmail());

    }

    @Test
    @Order(5)
    @DisplayName("Test de integração para buscra por id um person")
    void integrationTestDeletePerson() throws JsonProcessingException {

        given().spec(specification)
                .pathParam("id", person.getId())
                .when()
                    .delete("{id}")
                .then()
                    .statusCode(204);

    }

}
