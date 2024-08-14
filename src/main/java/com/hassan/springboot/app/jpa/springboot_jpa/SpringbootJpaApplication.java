package com.hassan.springboot.app.jpa.springboot_jpa;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.hassan.springboot.app.jpa.springboot_jpa.dto.PersonDto;
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
		// this.findOne();
		// create();
		// update();
		// delete();
		// personalizedQuery();
		// personalizedQueryDistinct();
		// personalizedQueryBetweenId();
		// queriesFunctionAggregation();
		subQueries();
	}

	@Transactional(readOnly = true)
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

	@Transactional(readOnly = true)
	public void findOne(){
		// Person person = null;
		// Optional<Person> optionalPerson = repository.findById(5L);
		// if(optionalPerson.isPresent()){
		// 	person = optionalPerson.get();
		// }
		// System.out.println(person);
		
		// Person person = repository.findById(8L).orElse(null);
		// System.out.println(person);

		//repository.findById(1L).ifPresent(p -> System.out.println(p));
		repository.findOneLikeName("ep").ifPresent(System.out::println );
	}

	@Transactional
	public void create(){
		//Scanner is not used in production as it implies transaction timeout 
		//risks, this is for testing purposes.
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please input your name:");
		String name = scanner.next();
		System.out.println("Please input your last name:");
		String lastName = scanner.next();
		System.out.println("Please input your preferred programming language:");
		String programmingLanguage = scanner.next();
		scanner.close();

		Person person = new Person(null, name, lastName, programmingLanguage);
		Person newPerson = repository.save(person);

		System.out.println(newPerson);
	}

	@Transactional
	public void update(){
		//Scanner is not used in production as it implies transaction timeout 
		//risks, this is for testing purposes
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please input the ID to change:");
		Long id = scanner.nextLong();

		Optional<Person> optionalPerson = repository.findById(id); 

		optionalPerson.ifPresent(p -> {
			System.out.println(p);
			System.out.println("Please input the programming language to update:");
			String programmingLanguage = scanner.next();
			p.setProgrammingLanguage(programmingLanguage);
			repository.save(p);
			System.out.println(p);
		});
		scanner.close();
	}

	@Transactional
	public void delete(){
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please input ID to delete");
		Long id = scanner.nextLong();
		repository.deleteById(id);

		scanner.close();
	}

	@Transactional(readOnly = true)
	public void personalizedQuery(){
		System.out.println("=====Fill and return DTO object with Name and from DB=====");
		List<PersonDto> personsDto = repository.findAllPersonDto();

		personsDto.stream().forEach(System.out::println);
	};	

	@Transactional(readOnly = true)
	public void personalizedQueryDistinct(){
		System.out.println("==========Find unique names==========");
		List<String> uniquePrograms = repository.findAllProgramDistinct();

		uniquePrograms.stream().forEach(System.out::println);
	};	
	
	@Transactional(readOnly = true)
	public void personalizedQueryConcatLowerAndUpperCase(){
		System.out.println("==========Find unique names==========");
		List<String> fullnameUpperLower = repository.findAllFullConcatNames();

		fullnameUpperLower.stream().forEach(System.out::println);
	};	

	@Transactional(readOnly = true)
	public void personalizedQueryBetweenId(){
		System.out.println("==========Find between IDs==========");
		List<Person> personsBetween = repository.findAllBetweenIdOrder();

		personsBetween.stream().forEach(System.out::println);
	}

	@Transactional(readOnly = true)
	public void queriesFunctionAggregation(){
		System.out.println("==========Find total of people in table==========");
		Long count = repository.totalPerson();
		System.out.println("Total count: " + count);

		
		System.out.println("==========Find between IDs==========");
		Long maxId = repository.getMaxId();
		Long minId = repository.getMinId();
		System.out.println("Minimum ID value is: " + minId + " Maximum ID value is: " + maxId);

		System.out.println("==========Find longest/shortest names==========");
		Integer longest = repository.getLongestNameValue();
		Integer shortest = repository.getShortestNameValue();
		System.out.println("Longest name value is: " + longest + " characters long.");
		System.out.println("Shortest name value is: " + shortest + " characters long.");

		System.out.println("==========Consult aggregation functions: min, max, sum, avg, count==========");
		Object[] resumeReg = (Object[]) repository.getSummaryAggregationFunction();
		System.out.println(
			    "min=" + resumeReg[0] +
				", max=" + resumeReg[1] +
				", sum=" + resumeReg[2] +
				", avg=" + resumeReg[3] +
		        ", count=" + resumeReg[4]);
	}

	@Transactional(readOnly = true)
	public void subQueries(){
		List<Object[]> shortestName = repository.findShortestName();

		shortestName.forEach(name -> {
			System.out.println("Shortest name is " + name[0]);
			System.out.println("With " + name[1] + " characters");
		});

		Optional<Person> lastRegistry = repository.findLastRegistry();
		lastRegistry.ifPresent(person -> System.out.println("Last registry: " + person));


	}
}
