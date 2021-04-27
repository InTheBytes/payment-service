package com.inthebytes.searchservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SearchserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SearchserviceApplication.class, args);
	}

//	@Bean
//	public Docket apiDocket() {
//
//		Docket docket =  new Docket(DocumentationType.SWAGGER_2)
//				.select()
//				.apis(RequestHandlerSelectors.basePackage("com.inthebytes.searchservice.control"))
//				.paths(PathSelectors.any())
//				.build();
//
//		return docket;
//
//	} 
}
