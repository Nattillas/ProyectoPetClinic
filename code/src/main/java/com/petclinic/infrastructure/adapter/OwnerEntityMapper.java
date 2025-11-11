package com.petclinic.infrastructure.adapter;

import com.petclinic.domain.model.Owner;
import com.petclinic.domain.model.Pet;
import com.petclinic.infrastructure.entity.OwnerEntity;
import com.petclinic.infrastructure.entity.PetEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper for converting between Owner domain models and JPA entities
 */
@Component
public class OwnerEntityMapper {
    
    public Owner toDomain(OwnerEntity entity) {
        if (entity == null) {
            return null;
        }
        
        Owner owner = new Owner();
        owner.setId(entity.getId());
        owner.setFirstName(entity.getFirstName());
        owner.setLastName(entity.getLastName());
        owner.setAddress(entity.getAddress());
        owner.setCity(entity.getCity());
        owner.setTelephone(entity.getTelephone());
        
        // Map pets (simple mapping to avoid circular reference)
        if (entity.getPets() != null) {
            List<Pet> pets = entity.getPets().stream()
                    .map(this::petToDomainSimple)
                    .collect(Collectors.toList());
            owner.setPets(pets);
        }
        
        return owner;
    }
    
    public OwnerEntity toEntity(Owner owner) {
        if (owner == null) {
            return null;
        }
        
        OwnerEntity entity = new OwnerEntity();
        entity.setId(owner.getId());
        entity.setFirstName(owner.getFirstName());
        entity.setLastName(owner.getLastName());
        entity.setAddress(owner.getAddress());
        entity.setCity(owner.getCity());
        entity.setTelephone(owner.getTelephone());
        
        return entity;
    }
    
    public List<Owner> toDomainList(List<OwnerEntity> entities) {
        if (entities == null) {
            return new ArrayList<>();
        }
        return entities.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }
    
    private Pet petToDomainSimple(PetEntity entity) {
        if (entity == null) {
            return null;
        }
        
        Pet pet = new Pet();
        pet.setId(entity.getId());
        pet.setName(entity.getName());
        pet.setBirthDate(entity.getBirthDate());
        
        // Don't map owner to avoid circular reference
        
        return pet;
    }
}