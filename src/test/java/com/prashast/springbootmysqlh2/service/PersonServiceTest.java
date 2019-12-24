package com.prashast.springbootmysqlh2.service;

import com.prashast.springbootmysqlh2.model.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.TestPropertySources;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.util.List;

@SpringBootTest
@TestPropertySource("classpath:application.properties")
public class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @BeforeEach
    public void setup(){
        personService.insertThousandRecords();
    }

    @Test
    public void testGetAllPersons(){
        List<Person> personList = personService.getAllPersons();

        Assertions.assertTrue(!personList.isEmpty());
    }

    @Test
    public void testGetAllPersonsInStreaming(){
        personService.getAllPersonsInStreamingViaJdbc(new ResponseBodyEmitter());
    }

}
