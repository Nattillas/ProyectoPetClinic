package com.petclinic.application.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO for PetType data transfer
 */
public class PetTypeDto {
    
    private Long id;
    
    @NotBlank(message = "Pet type name is required")
    private String name;
    
    // Default constructor
    public PetTypeDto() {}
    
    public PetTypeDto(String name) {
        this.name = name;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}