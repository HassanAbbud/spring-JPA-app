package com.hassan.springboot.app.jpa.springboot_jpa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.hassan.springboot.app.jpa.springboot_jpa.entities.Person;

public interface PersonRepository extends CrudRepository<Person, Long>{
    // Find via JPA method keywords (findby, Before, Or, And, etc.)
    List<Person> findByProgrammingLanguage(String programmingLanguage);
    List<Person> findByProgrammingLanguageAndName(String programmingLanguage, String name);

    // Find via custom query with @Query
    @Query("SELECT p FROM Person p WHERE p.programmingLanguage=?1 and p.name=?2")
    List<Person> customFindByProgrammingLanguageAndName(String programmingLanguage, String name);

}
