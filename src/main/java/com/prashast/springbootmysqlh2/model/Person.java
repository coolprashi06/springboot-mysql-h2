package com.prashast.springbootmysqlh2.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "person")
public class Person implements Serializable {

    private static final long serialVersionUID = 9075916046034338274L;

    @EmbeddedId
    private PersonKey personKey;

    @Column(name = "age")
    private int age;

    @CreationTimestamp
    @Column(name = "created_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @UpdateTimestamp
    @Column(name = "last_modified_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;


    public Person() {
    }

    public PersonKey getPersonKey() {
        return personKey;
    }

    public void setPersonKey(PersonKey personKey) {
        this.personKey = personKey;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public String toString() {
        return "Person{" +
                "personKey=" + personKey +
                ", age=" + age +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}
