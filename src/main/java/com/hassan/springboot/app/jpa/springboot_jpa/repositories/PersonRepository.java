package com.hassan.springboot.app.jpa.springboot_jpa.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.hassan.springboot.app.jpa.springboot_jpa.dto.PersonDto;
import com.hassan.springboot.app.jpa.springboot_jpa.entities.Person;

public interface PersonRepository extends CrudRepository<Person, Long>{

    //Sub Queries 
    @Query("SELECT p.name, LENGTH(p.name) FROM Person p WHERE LENGTH(p.name)=(SELECT MIN(LENGTH(p.name)) FROM Person p)")
    List<Object[]> findShortestName();
    @Query("SELECT p FROM Person p WHERE p.id=(SELECT MAX(p.id) FROM Person p)")
    Optional<Person> findLastRegistry();
    
    @Query("SELECT p FROM Person p WHERE p.id=?1")
    Optional<Person> findOne(Long id);
    
    @Query("SELECT p FROM Person p WHERE p.name=?1")
    Optional<Person> findOneName(String name);
    
    //Search by LIKE argument, % represent that it has to search left and right 
    @Query("SELECT p FROM Person p WHERE p.name LIKE %?1%")
    Optional<Person> findOneLikeName(String name);

    // CONCAT and return a full names
    // @Query("SELECT p.name || ' ' || p.lastname FROM Person p")
    @Query("SELECT CONCAT(LOWER(p.name), ' ', UPPER(p.lastname)) FROM Person p")
    List<String> findAllFullConcatNames();

    // Find range of IDs ordered by name. Order can be ASC (default) or DESC
    @Query("SELECT p FROM Person p WHERE p.id BETWEEN 2 AND 7 ORDER BY p.name DESC")
    List<Person> findAllBetweenIdOrder();
    
    //Get total count 
    @Query("SELECT COUNT(p) From Person p")
    Long totalPerson();

    //Get MIN and MAX IDs
    @Query("SELECT MAX(p.id) FROM Person p")
    Long getMaxId();
    
    @Query("SELECT MIN(p.id) FROM Person p")
    Long getMinId();

    // Find longest/shortest name
    @Query("SELECT MAX(LENGTH(p.name)) FROM Person p")
    Integer getLongestNameValue();
    
    @Query("SELECT MIN(LENGTH(p.name)) FROM Person p")
    Integer getShortestNameValue();

    // Find sum of IDs and average of name characters. Multiple queries in a single function. 
    @Query("SELECT MIN(p.id), MAX(p.id), SUM(p.id), AVG(length(p.name)), COUNT(p.id) FROM Person p")
    public Object getSummaryAggregationFunction();

    // Find unique programming languages
    @Query("SELECT DISTINCT(p.programmingLanguage) FROM Person p")
    List<String> findAllProgramDistinct();

    // Search LIKE argument with JPA keyword in method
    Optional<Person> findByNameContaining(String name);

    // Find via JPA method keywords (findBy, Before, Or, And, etc.)
    List<Person> findByProgrammingLanguage(String programmingLanguage);
    List<Person> findByProgrammingLanguageAndName(String programmingLanguage, String name);

    // Find via custom query with @Query
    @Query("SELECT p FROM Person p WHERE p.programmingLanguage=?1 AND p.name=?2")
    List<Person> customFindByProgrammingLanguageAndName(String programmingLanguage, String name);

    // Find specific fields instead of whole Object
    @Query("SELECT p.name, p.programmingLanguage FROM Person p")
    List<Object[]> customFindAsObject();

    // Fill DTO from found attributes name & lastname
    @Query("SELECT NEW com.hassan.springboot.app.jpa.springboot_jpa.dto.PersonDto(p.name, p.lastname) From Person p")
    List<PersonDto> findAllPersonDto();

    
}
