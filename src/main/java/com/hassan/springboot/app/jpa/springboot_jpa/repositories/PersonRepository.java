package com.hassan.springboot.app.jpa.springboot_jpa.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.hassan.springboot.app.jpa.springboot_jpa.entities.Person;

public interface PersonRepository extends CrudRepository<Person, Long>{
    // Find via JPA method keywords (findby, Before, Or, And, etc.)
    List<Person> findByProgrammingLanguage(String programmingLanguage);
    List<Person> findByProgrammingLanguageAndName(String programmingLanguage, String name);

}
