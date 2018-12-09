package com.twitterapp.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
//import org.springframework.data.mongodb.MongoDbFactory;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.mongodb.MongoClient;

//import com.mongodb.MongoClient;

@Configuration
@ComponentScan(basePackages = {"com.twitter","com.twitterapp"})
public class SpringMongoConfig {
	@Bean
	public MongoDbFactory mongoDbFactory() throws Exception {
		System.out.println("factory");
		return new SimpleMongoDbFactory(new MongoClient("localhost", 27017), "smartbeats");
	}

	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
		System.out.println("mongo");
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
		System.out.println("mongonext");
		return mongoTemplate;

	}

}