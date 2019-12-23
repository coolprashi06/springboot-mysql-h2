package com.prashast.springbootmysqlh2.service;

import com.prashast.springbootmysqlh2.model.Person;
import com.prashast.springbootmysqlh2.model.PersonKey;
import com.prashast.springbootmysqlh2.model.PersonRequest;
import com.prashast.springbootmysqlh2.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void savePerson(PersonRequest personRequest){
        PersonKey personKey = new PersonKey(personRequest.getFirstName(), personRequest.getLastName());
        Person person = new Person();
        person.setAge(personRequest.getAge());
        person.setPersonKey(personKey);

        personRepository.save(person);
    }

    public void insertThousandRecords(){
        for(int i = 21000; i<40000; i++){
            PersonKey personKey = new PersonKey("p"+i, "l"+i);
            Person person = new Person();
            person.setAge(32);
            person.setPersonKey(personKey);

            personRepository.save(person);
        }
    }

    public List<Person> getAllPersons(){
        Iterable<Person> persons= personRepository.findAll();

        List<Person> personList = StreamSupport.stream(persons.spliterator(), false).collect(Collectors.toList());
        return personList;
    }

    public List<Person> getAllPersonsViaJdbc(){
        jdbcTemplate.setFetchSize(100);


        List<Person> personList = new ArrayList<>();
        jdbcTemplate.query("select * from person", new ResultSetExtractor<Object>() {
            @Override
            public List<Person> extractData(ResultSet resultSet) throws SQLException {

                while (resultSet.next()){
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    int age = resultSet.getInt("age");
                    Date createdDate = resultSet.getDate("created_dt");
                    Date lastModifiedDate = resultSet.getDate("last_modified_dt");

                    PersonKey personKey = new PersonKey();
                    personKey.setFirstName(firstName);
                    personKey.setLastName(lastName);
                    Person p = new Person();
                    p.setPersonKey(personKey);
                    p.setAge(age);
                    p.setCreatedDate(createdDate);
                    p.setLastModifiedDate(lastModifiedDate);

                    personList.add(p);
                }
                return personList;
            }
        });

        System.out.println("personListSize :::" + personList.size());

        return personList;
    }

    public void getAllPersonsViaJdbcInStreaming(ResponseBodyEmitter emitter){
        jdbcTemplate.setFetchSize(100);

        jdbcTemplate.query("select * from person", new RowCallbackHandler() {

            int count = 0;
            int totalCount = 0;
            List<Person> personList = new ArrayList<>();

            @Override
            public void processRow(ResultSet resultSet) throws SQLException {

                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    int age = resultSet.getInt("age");
                    Date createdDate = resultSet.getDate("created_dt");
                    Date lastModifiedDate = resultSet.getDate("last_modified_dt");

                    PersonKey personKey = new PersonKey();
                    personKey.setFirstName(firstName);
                    personKey.setLastName(lastName);
                    Person p = new Person();
                    p.setPersonKey(personKey);
                    p.setAge(age);
                    p.setCreatedDate(createdDate);
                    p.setLastModifiedDate(lastModifiedDate);

                    personList.add(p);
                    count++;
                    totalCount++;

                    try{
                        if(count == 1000){
                            System.out.println(personList);
                            emitter.send(personList, MediaType.APPLICATION_JSON);
                            personList.clear();
                            count = 0;
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }

            }

        });

        emitter.complete();

    }

}
