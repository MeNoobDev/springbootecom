package com.springecom.major;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// admin id is admin@gmail.com pass is Admin.Pass@12

@SpringBootApplication
public class MajorApplication {

	public String PORT = System.getenv("PORT");
	public static void main(String[] args) {
		SpringApplication.run(MajorApplication.class, args);
	}

}
