package com.petclinic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for PetClinic
 * Following Spring Boot conventions and hexagonal architecture
 */
@SpringBootApplication
public class PetClinicApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(PetClinicApplication.class, args);
    }
}