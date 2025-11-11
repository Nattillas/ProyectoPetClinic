package com.petclinic.cucumber.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model class representing a Pet Type in the PetClinic API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PetType {
    
    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("name")
    private String name;
    
    // Default constructor
    public PetType() {}
    
    // Constructor with parameters
    public PetType(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    
    // Constructor with name only (for creation)
    public PetType(String name) {
        this.name = name;
    }
    
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
    
    @Override
    public String toString() {
        return "PetType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        PetType petType = (PetType) o;
        
        if (id != null ? !id.equals(petType.id) : petType.id != null) return false;
        return name != null ? name.equals(petType.name) : petType.name == null;
    }
    
    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}