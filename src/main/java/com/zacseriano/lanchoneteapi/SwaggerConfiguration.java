package com.zacseriano.lanchoneteapi;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zacseriano.lanchoneteapi.models.cliente.Cliente;
import com.zacseriano.lanchoneteapi.models.gestor.Gestor;

import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Classe que implementa as configurações necessárias para se usar o Swagger como uma API de documentação.
 */ 
@Configuration
@EnableSwagger2
public class SwaggerConfiguration{
	
	/**
	 * Método que configura todos os endpoints mapeados na documentação.
	 */ 
	@Bean
    public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2)
		        .select()	       
		        .apis(RequestHandlerSelectors.any())
		        .paths(PathSelectors.any())
		        .build()
		        .apiInfo(metaInfo())
				.ignoredParameterTypes(Gestor.class, Cliente.class)
				.globalOperationParameters(Arrays.asList(
						new ParameterBuilder()
						.name("Authorization")
						.description("Header para token JWT")
						.modelRef(new ModelRef("string"))
						.parameterType("header")
						.required(false)
						.build()));
		        		
    }
	
	/**
	 * Método que configura informações sobre a API. 
	 */ 
    @SuppressWarnings("rawtypes")
	private ApiInfo metaInfo() {

        ApiInfo apiInfo = new ApiInfo(
                "Jamba Lanches API",
                "API REST de gerenciamento de Lanchonetes",
                "1.0",
                "Terms of Service",
                new Contact("Zacarias Seriano Jr.", "https://github.com/zacseriano",
                        "zacns@ufpi.edu.br"),
                "Apache License Version 2.0",
                "https://www.apache.org/licesen.html", new ArrayList<VendorExtension>()
        );

        return apiInfo;
    }
}