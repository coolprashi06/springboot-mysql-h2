package com.prashast.springbootmysqlh2;

import com.prashast.springbootmysqlh2.model.PersonRequest;
import com.prashast.springbootmysqlh2.repository.PersonRepository;
import com.prashast.springbootmysqlh2.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
class SpringbootMysqlH2ApplicationTests {

	@Autowired
	private PersonService personService;

	@Test
	void contextLoads() {

		PersonRequest personRequest = new PersonRequest();
		personRequest.setAge(32);
		personRequest.setFirstName("prashast");
		personRequest.setLastName("saxena");

		personService.savePerson(personRequest);

		System.out.println(personService.getAllPersons());
	}

}
