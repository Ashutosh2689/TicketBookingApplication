package com.ashutosh.movieservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
public class MovieServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner checkMongo(MongoTemplate mongoTemplate) {
		return args -> {
			System.out.println("MongoDB Database = "
					+ mongoTemplate.getDb().getName());
		};
	}

}
