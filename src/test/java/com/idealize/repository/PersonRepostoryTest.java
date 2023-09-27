package com.idealize.repository;

import com.idealize.models.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class PersonRepostoryTest {

    private PersonRepository repostory;
    @Autowired
    public PersonRepostoryTest(PersonRepository repostory) {
        this.repostory = repostory;
    }

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

    @DisplayName("Tem que salvar um person na base de dados")
    @Test
    void testGivenPersonObjWhenSaveThenReturnSavePerson() {

        // When
        Person savedPerson;
        savedPerson = repostory.save(person);

        // Then
        assertNotNull(savedPerson);
        assertTrue(savedPerson.getId() > 0);

    }

//    @DisplayName("Tem que listar os person na base de dados")
//    @Test
//    void testGivenPersonListWhenFindAllThenReturnPersonList() {
//
//        Person person1 = new Person("Chico",
//                "Tripa",
//                "tripa49@gmail.com",
//                "Rua major celestino 1000 - Ant-Bezerra",
//                "male");
//
//        Person person2 = new Person("Chico",
//                "Tripa",
//                "tripa49@gmail.com",
//                "Rua major celestino 1000 - Ant-Bezerra",
//                "male");
//
//        // When
//        repostory.save(person1);
//        repostory.save(person2);
//
//        List<Person> list = repostory.findAll();
//
//        // Then
//        assertNotNull(list);
//        assertEquals(6, list.size());
//
//    }

    @DisplayName("Tem que buscar por id do person na base de dados")
    @Test
    void testGivenPersonObjWhenFindByIdThenReturnPersonObj() {

        // When
        repostory.save(person);

        Person personId = repostory.findById(person.getId()).get();

        // Then
        assertNotNull(personId);
        assertEquals(person.getId(), personId.getId());

    }

    @DisplayName("Tem que buscar por email do person na base de dados")
    @Test
    void testGivenPersonObjWhenFindByEmailThenReturnPersonObj() {

        // When
        repostory.save(person);

        Person personId = repostory.findByEmail(person.getEmail()).get();

        // Then
        assertNotNull(personId);
        assertEquals(person.getEmail(), personId.getEmail());
        assertEquals(person.getId(), personId.getId());

    }

    @DisplayName("Tem que atualizar dados de um person na base de dados")
    @Test
    void testGivenPersonObjWhenUpdateThenReturnPersonObj() {
        // When
        repostory.save(person);

        Person savedPerson = repostory.findById(person.getId()).get();
        savedPerson.setFirstName("Chico");
        savedPerson.setEmail("chico@gmail.com");

        Person updatePerson = repostory.save(savedPerson);

        // Then
        assertNotNull(updatePerson);
        assertEquals("Chico", updatePerson.getFirstName());
        assertEquals("chico@gmail.com", updatePerson.getEmail());

    }

    @DisplayName("Tem que deletar um person na base de dados")
    @Test
    void testGivenPersonObjWhenDelete() {

        // When
        repostory.save(person);
        repostory.deleteById(person.getId());
        Optional<Person> optional = repostory.findById(person.getId());

        // Then
        assertTrue(optional.isEmpty());

    }

    @DisplayName("Tem que buscar pelo nome e sobrenome de person na base de dados")
    @Test
    void testGivenFirstNameAndLastNameWhenFindByJPQLThenReturnPersonObj() {

        // When
        repostory.save(person);

        String firstName = "Leonardo";
        String lastName = "Costa";

        Person savedPerson = repostory.findByJPQL(firstName, lastName);

        // Then
        assertNotNull(savedPerson);
        assertEquals(firstName, savedPerson.getFirstName());
        assertEquals(lastName, savedPerson.getLastName());

    }

    @DisplayName("Tem que buscar pelo nome e sobre nome de person na base de dados")
    @Test
    void testGivenFirstNameAndLastNameWhenFindByJPQLNameParametersThenReturnPersonObj() {

        // When
        repostory.save(person);

        String firstName = "Leonardo";
        String lastName = "Costa";

        Person savedPerson = repostory.findByJPQLNameParameters(firstName, lastName);

        // Then
        assertNotNull(savedPerson);
        assertEquals(firstName, savedPerson.getFirstName());
        assertEquals(lastName, savedPerson.getLastName());

    }

    @DisplayName("Tem que buscar pelo nome e sobre nome de person na base de dados")
    @Test
    void testGivenFirstNameAndLastNameWhenFindByNativeSQLParametersThenReturnPersonObj() {

        // When
        repostory.save(person);

        String firstName = "Leonardo";
        String lastName = "Costa";

        Person savedPerson = repostory.findByNativeSQL(firstName, lastName);

        // Then
        assertNotNull(savedPerson);
        assertEquals(firstName, savedPerson.getFirstName());
        assertEquals(lastName, savedPerson.getLastName());

    }

    @DisplayName("Tem que buscar pelo nome e sobre nome de person na base de dados")
    @Test
    void testGivenFirstNameAndLastNameWhenFindByNativeSQLwitNameParametersThenReturnPersonObj() {

        // When
        repostory.save(person);

        String firstName = "Leonardo";
        String lastName = "Costa";

        Person savedPerson = repostory.findByNativeSQLwitNameParameters(firstName, lastName);

        // Then
        assertNotNull(savedPerson);
        assertEquals(firstName, savedPerson.getFirstName());
        assertEquals(lastName, savedPerson.getLastName());

    }
}