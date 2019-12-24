package com.prashast.springbootmysqlh2.repository;

import com.prashast.springbootmysqlh2.model.Person;
import com.prashast.springbootmysqlh2.model.PersonKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.QueryHint;
import java.util.stream.Stream;

@Repository
public interface PersonRepository extends JpaRepository<Person, PersonKey> {

    @QueryHints(value = @QueryHint(name = "org.hibernate.fetchSize", value = "500"))
    @Query(value = "select P from Person P")
    Stream<Person> streamAll();
}
