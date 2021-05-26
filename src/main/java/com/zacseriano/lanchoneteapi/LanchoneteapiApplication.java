package com.zacseriano.lanchoneteapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import io.swagger.annotations.Api;

/**
 * Classe que inicia a aplicação.
 */ 
@SpringBootApplication
@EnableAutoConfiguration
@EnableSpringDataWebSupport
@EnableCaching
@Api(value="Jamba Lanches API")
public class LanchoneteapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LanchoneteapiApplication.class, args);
	}
	
}
