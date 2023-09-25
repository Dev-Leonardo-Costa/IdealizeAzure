package com.idealize.controllers;

import com.idealize.models.Person;
import com.idealize.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    private PersonService service;
    @Autowired
    public PersonController(PersonService service) {
        this.service = service;
    }

    @GetMapping("/{idPerson}")
    public ResponseEntity<Person> findById(@PathVariable Long idPerson) {
        try {
            return ResponseEntity.ok(service.findById(idPerson));
        } catch (Exception ex){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public List<Person> listPersons(){
        return service.list();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Person create(@RequestBody Person person){
        return service.createPerson(person);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping
    public ResponseEntity<Person> update(@RequestBody Person person){
        try {
            return ResponseEntity.ok(service.updatePerson(person));
        }catch (Exception ex){
            return ResponseEntity.notFound().build();
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{idPerson}")
    public void delete(@PathVariable Long idPerson){
        service.deletePerson(idPerson);
    }

}
