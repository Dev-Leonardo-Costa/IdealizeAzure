package com.idealize.controllers;

import com.idealize.models.Person;
import com.idealize.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService service;

    @GetMapping("/{idPerson}")
    public Person findById(@PathVariable String idPerson) {
        return service.findById(idPerson);
    }

    @GetMapping
    public List<Person> listPersons(){
        return service.list();
    }

    @PostMapping
    public Person create(@RequestBody Person person){
        return service.createPerson(person);
    }
    @PutMapping
    public Person update(@RequestBody Person person){
        return service.updatePerson(person);
    }

    @DeleteMapping("/{idPerson}")
    public void delete(@PathVariable String idPerson){
        service.deletePerson(idPerson);
    }
}
