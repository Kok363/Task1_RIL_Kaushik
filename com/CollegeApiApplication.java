package com; 

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication //mark this class as a Spring Boot application
@ComponentScan(basePackages={"com","controller","entity","service"})

public class CollegeApiApplication { 
    public static void main(String[] args) { 
        SpringApplication.run(CollegeApiApplication.class, args); // Run
    }

@Bean
public RestTemplate restTemplate() {
    return new RestTemplate();
}
}
