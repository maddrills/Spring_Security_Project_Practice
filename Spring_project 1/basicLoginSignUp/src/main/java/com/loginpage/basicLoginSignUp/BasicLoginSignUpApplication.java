package com.loginpage.basicLoginSignUp;

import com.loginpage.basicLoginSignUp.Services.PersonDetailsService;
import com.loginpage.basicLoginSignUp.entity.Authorities;
import com.loginpage.basicLoginSignUp.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class BasicLoginSignUpApplication {

	@Autowired
	PersonDetailsService personDetailsService;
	public static void main(String[] args) {
		SpringApplication.run(BasicLoginSignUpApplication.class, args);

	}

	@Bean
	CommandLineRunner runner(PersonDetailsService personDetailsService){
		return (args) -> {
			//addAUser(personDetailsService);
			//addAuthority(personDetailsService);
			//displayAllAuthority(personDetailsService);
			//getPersonByName(personDetailsService);

			//personDetailsService.addARollToAUser(new Authorities("Admin"), new Person("JEEN",26, "mat@matify.com","hsbc"));

			personDetailsService.updateAPersonsRoles("Mathew Francis","Admin");
		};
	}

	public void getPersonByName(PersonDetailsService personDetailsService){
		System.out.println(personDetailsService.getPersonByName("Mathew Francis").getName());
	}

	public void addAuthority(PersonDetailsService personDetailsService){
		personDetailsService.addARoll(new Authorities("Admin"));

	}
	public void addAUser(PersonDetailsService personDetailsService){
		Person mat = new Person("Mathew Francis",26, "mat@matify.com","hsbc");
		//mat.addRoles();
		personDetailsService.addAPerson(mat);
	}

	public void displayAllAuthority(PersonDetailsService personDetailsService){
		//System.out.println(Authorities.class);
		System.out.println(personDetailsService.getAllAuthorities("Admin").getAuthority());
	}




}
