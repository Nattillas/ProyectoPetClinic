package com.petclinic.infrastructure.web.dto;

import java.util.Objects;

/**
 * Data Transfer Object for PetType.
 */
public class PetTypeDto {
    private Long id;
    private String name;

    public PetTypeDto() {}

    public PetTypeDto(Long id, String name) {
        this.id = id;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PetTypeDto that = (PetTypeDto) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "PetTypeDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}