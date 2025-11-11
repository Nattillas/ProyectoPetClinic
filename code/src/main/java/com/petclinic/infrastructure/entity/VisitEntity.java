package com.petclinic.infrastructure.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Visit JPA Entity for database persistence
 */
@Entity
@Table(name = "visits")
public class VisitEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "visit_date", nullable = false)
    private LocalDate date;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    private PetEntity pet;
    
    // Default constructor
    public VisitEntity() {}
    
    public VisitEntity(LocalDate date, String description) {
        this.date = date;
        this.description = description;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public PetEntity getPet() {
        return pet;
    }
    
    public void setPet(PetEntity pet) {
        this.pet = pet;
    }
}