package com.epam.training.ticketservice.presentation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.epam.training.ticketservice")
public class CliApplication {
    public static void main(String[] args) {
        SpringApplication.run(CliApplication.class, args);
    }
}
