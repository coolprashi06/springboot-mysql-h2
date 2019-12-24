package com.prashast.springbootmysqlh2.controller;

import com.prashast.springbootmysqlh2.model.Person;
import com.prashast.springbootmysqlh2.model.PersonRequest;
import com.prashast.springbootmysqlh2.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @RequestMapping(value = "/",consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity addPerson(@RequestBody PersonRequest personRequest){
        personService.savePerson(personRequest);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/insertHugeData", method = RequestMethod.PUT)
    public ResponseEntity insertHugeData(){
        personService.insertThousandRecords();
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/getAllViaJPA",produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<List<Person>> getAll(){
        List<Person> personList = personService.getAllPersons();
        return new ResponseEntity(personList, HttpStatus.OK);
    }

    @RequestMapping(value = "/getAllInStreamingViaJPA",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseBodyEmitter> getAllInStreamingViaJPA(){
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();
        personService.getAllPersonsInStreamingviaJPA(emitter);
        return new ResponseEntity(emitter, HttpStatus.OK);
    }

    @RequestMapping(value = "/getAllViaJdbc",produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<List<Person>> getAllViaJdbc(){
        List<Person> personList = personService.getAllPersonsViaJdbc();
        return new ResponseEntity(personList, HttpStatus.OK);
    }

    @RequestMapping(value = "/getAllInStreamingViaJDBC", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<ResponseBodyEmitter> doStreaming(){
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();
        personService.getAllPersonsInStreamingViaJdbc(emitter);

        return new ResponseEntity<>(emitter, HttpStatus.OK);
    }

    @RequestMapping(value = "/getAllInStreamingOldFashion", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<ResponseBodyEmitter> getAllInStreamingOldFashion(){
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();
        personService.doStreamOldFashion(emitter);

        return new ResponseEntity<>(emitter, HttpStatus.OK);
    }
}
