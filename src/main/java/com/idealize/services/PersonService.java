package com.idealize.services;

import com.idealize.exceptions.ResourceNotFoundException;
import com.idealize.models.Person;
import com.idealize.repository.PersonRepostory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonRepostory repostory;

    public Person findById(Long id) {
        return repostory.findById(id).orElseThrow(() -> new ResourceNotFoundException("Person não existe"));
    }

    public List<Person> list() {
        return repostory.findAll();
    }

    public Person createPerson(Person person) {
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
