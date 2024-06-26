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

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;


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

//			personDetailsService.updateAPersonsRoles("John Wick","Admin");
//			personDetailsService.addAPersonWithAnExistingRole(
//					new Person("Kelly",26, "mat@matify.com","hsbc"),
//					new HashSet<>(List.of("Admin"))
//			);

//			addAUser(personDetailsService);
//			updateTheRoleOfUser(personDetailsService);
			personDetailsService.getAllUsersRole_user().forEach((Person value) -> System.out.println(value.getName()));

		};
	}

	public void getPersonByName(PersonDetailsService personDetailsService){
		System.out.println(personDetailsService.getPersonByName("Mathew Francis").getName());
	}

	public void addAuthority(PersonDetailsService personDetailsService){
		personDetailsService.addARoll(new Authorities("Admin"));

	}
	public void addAUser(PersonDetailsService personDetailsService){
		Person mat = new Person("John Wick",26, "mat@matify.com","hsbc");
		Person mat1 = new Person("Ron",26, "RonAdminUser@matify.com","12345");
		//mat.addRoles();
		personDetailsService.addAPerson(mat);
		personDetailsService.addAPerson(mat1);
	}

	public void displayAllAuthority(PersonDetailsService personDetailsService){
		//System.out.println(Authorities.class);
		System.out.println(personDetailsService.getAllAuthorities("Admin").getAuthority());
	}

	public void updateTheRoleOfUser(PersonDetailsService personDetailsService){
		personDetailsService.updateAPersonsRoles("Ron","Admin");
		personDetailsService.updateAPersonsRoles("Ron","User");
		personDetailsService.updateAPersonsRoles("John Wick", "Admin");
	}



}
