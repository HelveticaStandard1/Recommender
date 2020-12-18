package com.personal.Recommender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RecommenderApplication {

	private static Logger LOG = LoggerFactory.getLogger(RecommenderApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RecommenderApplication.class, args);
	}

}
