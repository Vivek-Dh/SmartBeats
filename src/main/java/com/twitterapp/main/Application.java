package com.twitterapp.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages= {"com.twitterapp","com.twitter"})
public class Application {
	public static void main(String[] args) {
		System.out.print("S");
		SpringApplication.run(Application.class, args);
	}
}
