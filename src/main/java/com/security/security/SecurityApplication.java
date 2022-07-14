package com.security.security;

import com.security.security.model.User;
import com.security.security.repository.UserRepository;
import com.security.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SecurityApplication {


	public static void main(String[] args) {

		SpringApplication.run(SecurityApplication.class, args);

	}

}
