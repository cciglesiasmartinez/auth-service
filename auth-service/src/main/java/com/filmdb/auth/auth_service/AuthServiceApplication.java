package com.filmdb.auth.auth_service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@OpenAPIDefinition(info = @Info(title = "Auth Service API", version = "v1"))
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.filmdb.auth.auth_service.repository")
@EntityScan(basePackages = "com.filmdb.auth.auth_service.entity")
public class AuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}

}
