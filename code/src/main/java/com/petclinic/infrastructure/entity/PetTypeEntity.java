package com.petclinic.infrastructure.entity;

import jakarta.persistence.*;

/**
 * PetType JPA Entity for database persistence
 */
@Entity
@Table(name = "pet_types")
public class PetTypeEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 50)
    private String name;
    
    // Default constructor
    public PetTypeEntity() {}
    
    public PetTypeEntity(String name) {
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