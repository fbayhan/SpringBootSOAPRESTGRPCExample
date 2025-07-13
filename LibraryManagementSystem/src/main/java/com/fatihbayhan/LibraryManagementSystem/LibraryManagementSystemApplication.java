package com.fatihbayhan.LibraryManagementSystem;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fatihbayhan.LibraryManagementSystem.enums.UserRole;
import com.fatihbayhan.LibraryManagementSystem.model.User;
import com.fatihbayhan.LibraryManagementSystem.repository.UserRepository;

@SpringBootApplication
public class LibraryManagementSystemApplication {
	


	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementSystemApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner commandLineRunner() {
//		return args -> createUser();
//	}
//
//	public void createUser(){
//		User user = new User();
//		user.setEmail("sdadsa@dffsa.com");
//		user.setRole(UserRole.LIBRARIAN);
//		user.setPassword("1234");
//		user.setFullName("Fatih Bayhan");
//		userRepository.save(user);
//	}
}
