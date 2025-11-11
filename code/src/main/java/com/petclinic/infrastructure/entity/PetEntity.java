package com.petclinic.infrastructure.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Pet JPA Entity for database persistence
 */
@Entity
@Table(name = "pets")
public class PetEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", nullable = false)
    private PetTypeEntity type;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private OwnerEntity owner;
    
    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VisitEntity> visits = new ArrayList<>();
    
    // Default constructor
    public PetEntity() {}
    
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
    
    public PetTypeEntity getType() {
        return type;
    }
    
    public void setType(PetTypeEntity type) {
        this.type = type;
    }
    
    public OwnerEntity getOwner() {
        return owner;
    }
    
    public void setOwner(OwnerEntity owner) {
        this.owner = owner;
    }
    
    public List<VisitEntity> getVisits() {
        return visits;
    }
    
    public void setVisits(List<VisitEntity> visits) {
        this.visits = visits;
    }
}