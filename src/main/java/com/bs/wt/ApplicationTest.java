package com.bs.wt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.bs.wt")
public class ApplicationTest {
	public static void main(String[] args) {
		SpringApplication.run(ApplicationTest.class,args);
	}
}
