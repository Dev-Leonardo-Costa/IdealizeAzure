package com.idealize.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idealize.exceptions.ResourceNotFoundException;
import com.idealize.models.Person;
import com.idealize.services.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private PersonService service;

    private Person person;

    @BeforeEach
    public void setup() {
        // Given
        person = new Person(1L, "Leonardo",
                "Costa",
                "leo4921@gmail.com",
                "Rua major celestino 1045 - Ant-Bezerra",
                "male");
    }

    @Test
    void create() throws Exception {

        // Given
        given(service.createPerson(any(Person.class))).willAnswer((invocation) -> invocation.getArgument(0));

        // When
        ResultActions response = mockMvc.perform(post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(person)));

        // Then
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(person.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(person.getLastName())))
                .andExpect(jsonPath("$.email", is(person.getEmail())));

    }

    @Test
    void findById() throws Exception {

        Long personId = 1L;

        // Given
        given(service.findById(personId)).willReturn((person));

        // When
        ResultActions response = mockMvc.perform(get("/person/{id}", personId));

        // Then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(person.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(person.getLastName())))
                .andExpect(jsonPath("$.email", is(person.getEmail())));
    }

    @Test
    void findByIdException() throws Exception {

        Long personId = 1L;

        // Given
        given(service.findById(personId)).willThrow(ResourceNotFoundException.class);

        // When
        ResultActions response = mockMvc.perform(get("/person/{id}", personId));

        // Then
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void listPersons() throws Exception {

        Person person2 = new Person(1L, "Chico",
                "Silva",
                "chico21@gmail.com",
                "Rua major celestino 1045 - Ant-Bezerra",
                "male");

        List<Person> persons = new ArrayList<>();
        persons.add(person);
        persons.add(person2);

        // Given
        given(service.list()).willReturn((persons));

        // When
        ResultActions response = mockMvc.perform(get("/person"));
        // Then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(persons.size())));

    }

    @Test
    void update() throws Exception {

        var personAtualizado = new Person("Chico",
                "Silva",
                "chico21@gmail.com",
                "Rua major celestino 1045 - Ant-Bezerra",
                "femia");

        Long id = 1L;
        given(service.findById(id)).willReturn(person);
        given(service.updatePerson(any())).willAnswer( (invoction) -> invoction.getArgument(0));

        ResultActions response = mockMvc.perform(put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(personAtualizado)));

        // Then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(personAtualizado.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(personAtualizado.getLastName())))
                .andExpect(jsonPath("$.email", is(personAtualizado.getEmail())));


    }
    @Test
    void testNegativoDoUpdadeNotFound() throws Exception {

        var personAtualizado = new Person("Chico",
                "Silva",
                "chico21@gmail.com",
                "Rua major celestino 1045 - Ant-Bezerra",
                "femia");

        Long id = 1L;
        given(service.findById(id)).willThrow(ResourceNotFoundException.class);
        given(service.updatePerson(any())).willAnswer( (invoction) -> invoction.getArgument(1));

        ResultActions response = mockMvc.perform(put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(personAtualizado)));

        // Then
        response.andExpect(status().isNotFound())
                .andDo(print());

    }

    @Test
    @DisplayName("JUnit test for Given personId when Delete then Return NotContent")
    void testGivenPersonId_WhenDelete_thenReturnNotContent() throws JsonProcessingException, Exception {

        // Given / Arrange
        long personId = 1L;
        willDoNothing().given(service).deletePerson(personId);

        // When / Act
        ResultActions response = mockMvc.perform(delete("/person/{id}", personId));

        // Then / Assert
        response.
                andExpect(status().isNoContent())
                .andDo(print());
    }
}