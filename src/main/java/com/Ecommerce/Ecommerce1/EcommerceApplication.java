package com.Ecommerce.Ecommerce1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EcommerceApplication {

	private final static  Logger logger = LoggerFactory.getLogger(EcommerceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
		System.out.println("Application started !  1000% ");
		//logger.info("hey buddy");
		logger.info(" Ecommerce Application ");
	}
}
