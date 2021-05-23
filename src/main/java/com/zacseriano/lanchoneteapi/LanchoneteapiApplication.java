package com.zacseriano.lanchoneteapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.annotations.Api;

/**
 * Classe que inicia a aplicação.
 */ 
@SpringBootApplication
@EnableAutoConfiguration
@Api(value="Jamba Lanches API")
public class LanchoneteapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LanchoneteapiApplication.class, args);
	}
	
}
