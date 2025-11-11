package com.petclinic.config;

import com.petclinic.domain.port.OwnerRepositoryPort;
import com.petclinic.domain.port.PetRepositoryPort;
import com.petclinic.domain.port.PetTypeRepositoryPort;
import com.petclinic.domain.service.OwnerService;
import com.petclinic.domain.service.PetService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for dependency injection following hexagonal architecture
 * This configuration wires the domain services with their repository ports
 */
@Configuration
public class DomainConfiguration {
    
    @Bean
    public PetService petService(PetRepositoryPort petRepositoryPort, OwnerRepositoryPort ownerRepositoryPort) {
        return new PetService(petRepositoryPort, ownerRepositoryPort);
    }
    
    @Bean
    public OwnerService ownerService(OwnerRepositoryPort ownerRepositoryPort) {
        return new OwnerService(ownerRepositoryPort);
    }
}