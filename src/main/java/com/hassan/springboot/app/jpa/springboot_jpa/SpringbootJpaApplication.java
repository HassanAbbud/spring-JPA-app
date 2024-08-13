package com.hassan.springboot.app.jpa.springboot_jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.hassan.springboot.app.jpa.springboot_jpa.entities.Person;
import com.hassan.springboot.app.jpa.springboot_jpa.repositories.PersonRepository;

@SpringBootApplication
public class SpringbootJpaApplication implements CommandLineRunner{
	
	@Autowired
	private PersonRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// list();
		findOne();

	}

	public void list(){
		// List<Person> persons = (List<Person>) repository.findAll();
		// By JPA keyword
		// List<Person> persons = (List<Person>) repository.findByProgrammingLanguage("Java");
		// List<Person> persons = (List<Person>) repository.findByProgrammingLanguageAndName("Java", "Andres");
		
		// By @Query
		List<Person> persons = (List<Person>) repository.customFindByProgrammingLanguageAndName("Java", "Andres");

		persons.stream().forEach(person -> {
			System.out.println(person);
		});	
		
		List<Object[]> personsValue = repository.customFindAsObject();
		personsValue.stream().forEach(person -> {
			System.out.println(person[0] + " is an expert in " + person[1]);
		});	
	}

	public void findOne(){
		// Person person = null;
		// Optional<Person> optionalPerson = repository.findById(5L);
		// if(optionalPerson.isPresent()){
		// 	person = optionalPerson.get();
		// }
		// System.out.println(person);
		
		// Person person = repository.findById(8L).orElse(null);
		// System.out.println(person);

		repository.findById(8L).ifPresent(p -> System.out.println(p));
	}

}
