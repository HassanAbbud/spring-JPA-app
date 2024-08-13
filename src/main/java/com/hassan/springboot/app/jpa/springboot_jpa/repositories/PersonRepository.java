package com.hassan.springboot.app.jpa.springboot_jpa.repositories;

import org.springframework.data.repository.CrudRepository;

import com.hassan.springboot.app.jpa.springboot_jpa.entities.Person;

public interface PersonRepository extends CrudRepository<Person, Long>{

    
}
