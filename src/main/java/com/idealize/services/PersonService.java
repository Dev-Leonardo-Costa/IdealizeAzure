package com.idealize.services;

import com.idealize.exceptions.ResourceNotFoundException;
import com.idealize.models.Person;
import com.idealize.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private PersonRepository repostory;
    @Autowired
    public PersonService(PersonRepository repostory) {
        this.repostory = repostory;
    }

    public Person findById(Long id) {
        return repostory.findById(id).orElseThrow(() -> new ResourceNotFoundException("Person não existe"));
    }

    public List<Person> list() {
        return repostory.findAll();
    }

    public Person createPerson(Person person) {

        Optional<Person> savedPerson = repostory.findByEmail(person.getEmail());
        if (savedPerson.isPresent()){
            throw new ResourceNotFoundException("Email já existe, informe outro que na seja igual a esse: " + person.getEmail());
        }

        return repostory.save(person);
    }

    public Person updatePerson(Person person) {

        var entity = repostory.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Person não existe"));
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return repostory.save(person);
    }

    public void deletePerson(Long idPerson) {
        var entity = repostory.findById(idPerson)
                .orElseThrow(() -> new ResourceNotFoundException("Person não existe"));
        repostory.delete(entity);
    }

}
