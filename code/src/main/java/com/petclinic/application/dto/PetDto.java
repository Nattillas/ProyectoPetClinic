package com.petclinic.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import java.time.LocalDate;

/**
 * DTO for Pet data transfer
 */
public class PetDto {
    
    private Long id;
    
    @NotBlank(message = "Pet name is required")
    private String name;
    
    @NotNull(message = "Birth date is required")
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;
    
    @NotNull(message = "Pet type is required")
    private PetTypeDto type;
    
    private OwnerDto owner;
    
    // Default constructor
    public PetDto() {}
    
    public PetDto(String name, LocalDate birthDate, PetTypeDto type) {
        this.name = name;
        this.birthDate = birthDate;
        this.type = type;
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
    
    public LocalDate getBirthDate() {
        return birthDate;
    }
    
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
    
    public PetTypeDto getType() {
        return type;
    }
    
    public void setType(PetTypeDto type) {
        this.type = type;
    }
    
    public OwnerDto getOwner() {
        return owner;
    }
    
    public void setOwner(OwnerDto owner) {
        this.owner = owner;
    }
}