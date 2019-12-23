package com.prashast.springbootmysqlh2.service;

import com.prashast.springbootmysqlh2.model.Person;
import com.prashast.springbootmysqlh2.model.PersonKey;
import com.prashast.springbootmysqlh2.model.PersonRequest;
import com.prashast.springbootmysqlh2.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public void savePerson(PersonRequest personRequest){
        PersonKey personKey = new PersonKey(personRequest.getFirstName(), personRequest.getLastName());
        Person person = new Person();
        person.setAge(personRequest.getAge());
        person.setPersonKey(personKey);

        personRepository.save(person);
    }

    public List<Person> getAllPersons(){
        Iterable<Person> persons= personRepository.findAll();

        List<Person> personList = StreamSupport.stream(persons.spliterator(), false).collect(Collectors.toList());
        return personList;
    }

}
