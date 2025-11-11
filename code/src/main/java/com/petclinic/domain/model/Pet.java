package com.petclinic.domain.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Pet domain model following hexagonal architecture principles
 */
public class Pet {
    private Long id;
    private String name;
    private LocalDate birthDate;
    private PetType type;
    private Owner owner;
    private List<Visit> visits;
    
    public Pet() {
        this.visits = new ArrayList<>();
    }
    
    public Pet(String name, LocalDate birthDate, PetType type) {
        this();
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
    
    public PetType getType() {
        return type;
    }
    
    public void setType(PetType type) {
        this.type = type;
    }
    
    public Owner getOwner() {
        return owner;
    }
    
    public void setOwner(Owner owner) {
        this.owner = owner;
    }
    
    public List<Visit> getVisits() {
        return visits;
    }
    
    public void setVisits(List<Visit> visits) {
        this.visits = visits != null ? visits : new ArrayList<>();
    }
    
    public void addVisit(Visit visit) {
        if (this.visits == null) {
            this.visits = new ArrayList<>();
        }
        this.visits.add(visit);
        visit.setPet(this);
    }
    
    public int getAge() {
        if (birthDate == null) {
            return 0;
        }
        return LocalDate.now().getYear() - birthDate.getYear();
    }
    
    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", type=" + type +
                '}';
    }
}