package com.idealize.services;

import com.idealize.exceptions.ResourceNotFoundException;
import com.idealize.models.Person;
import com.idealize.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private PersonRepository repository;

    @InjectMocks
    private PersonService service;

    private Person person;

    @BeforeEach
    public void setup() {
        // Given
        person = new Person("Leonardo",
                "Costa",
                "leo4921@gmail.com",
                "Rua major celestino 1045 - Ant-Bezerra",
                "male");
    }

    @DisplayName("Tem que cadastrar")
    @Test
    void testGivenPersonObjWhenSavePersonThenReturnPersonObj() {

        // Given
        given(repository.findByEmail(anyString())).willReturn(Optional.empty());
        given(repository.save(person)).willReturn(person);

        // When
        Person savedPerson = service.createPerson(person);

        assertNotNull(savedPerson);
        assertEquals("Leonardo", savedPerson.getFirstName());

    }

    @DisplayName("Tem que cadastrar lacçar um erro da validação")
    @Test
    void testGivenPersonObjWhenSavePersonThenThrowsException() {

        // Given
        given(repository.findByEmail(anyString())).willReturn(Optional.of(person));

        // When
        final ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            service.createPerson(person);
        });

        // then
        assertEquals("Email já existe, informe outro que na seja igual a esse: " + person.getEmail(), exception.getMessage());
    }

    @DisplayName("Tem que retorna um person por id")
    @Test
    void findById() {

        given(repository.findById(anyLong())).willReturn(Optional.of(person));

        Person personBuscado = service.findById(1L);

        assertNotNull(personBuscado);
        assertEquals("Leonardo", personBuscado.getFirstName());

    }

    @DisplayName("Tem que Verificar se tem os 2 person")
    @Test
    void list() {
        Person person2 = new Person("Chico",
                "Costa",
                "chico21@gmail.com",
                "Rua major celestino 555 - Ant-Bezerra",
                "male");

        given(repository.findAll()).willReturn(List.of(person, person2));

        List<Person> personList = service.list();

        assertNotNull(personList);
        assertEquals(2, personList.size());
    }

    @DisplayName("Tem que Verificar se esta vazio")
    @Test
    void listEmpty() {

        given(repository.findAll()).willReturn(Collections.emptyList());

        List<Person> personList = service.list();

        assertTrue(personList.isEmpty());
        assertEquals(0, personList.size());
    }

    @DisplayName("Tem que atualizar um person")
    @Test
    void updatePerson() {

        person.setId(1L);
        given(repository.findById(anyLong())).willReturn(Optional.of(person));

        person.setFirstName("Leo");
        person.setGender("Macho");

        given(repository.save(person)).willReturn(person);

        Person personUp = service.updatePerson(person);

        assertNotNull(personUp);
        assertEquals("Leo", personUp.getFirstName());
        assertEquals("Macho", personUp.getGender());

    }

    @Test
    void deletePerson() {

        person.setId(1L);
        given(repository.findById(anyLong())).willReturn(Optional.of(person));

        willDoNothing().given(repository).delete(person);

        service.deletePerson(person.getId());

        verify(repository, times(1)).delete(person);

    }
}