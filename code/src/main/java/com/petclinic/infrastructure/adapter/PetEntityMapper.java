package com.petclinic.infrastructure.adapter;

import com.petclinic.domain.model.Pet;
import com.petclinic.domain.model.PetType;
import com.petclinic.domain.model.Owner;
import com.petclinic.domain.model.Visit;
import com.petclinic.infrastructure.entity.PetEntity;
import com.petclinic.infrastructure.entity.PetTypeEntity;
import com.petclinic.infrastructure.entity.OwnerEntity;
import com.petclinic.infrastructure.entity.VisitEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper for converting between Pet domain models and JPA entities
 */
@Component
public class PetEntityMapper {
    
    public Pet toDomain(PetEntity entity) {
        if (entity == null) {
            return null;
        }
        
        Pet pet = new Pet();
        pet.setId(entity.getId());
        pet.setName(entity.getName());
        pet.setBirthDate(entity.getBirthDate());
        
        // Map pet type
        if (entity.getType() != null) {
            PetType petType = new PetType();
            petType.setId(entity.getType().getId());
            petType.setName(entity.getType().getName());
            pet.setType(petType);
        }
        
        // Map owner
        if (entity.getOwner() != null) {
            Owner owner = new Owner();
            owner.setId(entity.getOwner().getId());
            owner.setFirstName(entity.getOwner().getFirstName());
            owner.setLastName(entity.getOwner().getLastName());
            owner.setAddress(entity.getOwner().getAddress());
            owner.setCity(entity.getOwner().getCity());
            owner.setTelephone(entity.getOwner().getTelephone());
            pet.setOwner(owner);
        }
        
        // Map visits
        if (entity.getVisits() != null) {
            List<Visit> visits = entity.getVisits().stream()
                    .map(this::visitToDomain)
                    .collect(Collectors.toList());
            pet.setVisits(visits);
        }
        
        return pet;
    }
    
    public PetEntity toEntity(Pet pet) {
        if (pet == null) {
            return null;
        }
        
        PetEntity entity = new PetEntity();
        entity.setId(pet.getId());
        entity.setName(pet.getName());
        entity.setBirthDate(pet.getBirthDate());
        
        // Map pet type
        if (pet.getType() != null) {
            PetTypeEntity petTypeEntity = new PetTypeEntity();
            petTypeEntity.setId(pet.getType().getId());
            petTypeEntity.setName(pet.getType().getName());
            entity.setType(petTypeEntity);
        }
        
        // Map owner
        if (pet.getOwner() != null) {
            OwnerEntity ownerEntity = new OwnerEntity();
            ownerEntity.setId(pet.getOwner().getId());
            ownerEntity.setFirstName(pet.getOwner().getFirstName());
            ownerEntity.setLastName(pet.getOwner().getLastName());
            ownerEntity.setAddress(pet.getOwner().getAddress());
            ownerEntity.setCity(pet.getOwner().getCity());
            ownerEntity.setTelephone(pet.getOwner().getTelephone());
            entity.setOwner(ownerEntity);
        }
        
        return entity;
    }
    
    public List<Pet> toDomainList(List<PetEntity> entities) {
        if (entities == null) {
            return new ArrayList<>();
        }
        return entities.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }
    
    private Visit visitToDomain(VisitEntity entity) {
        if (entity == null) {
            return null;
        }
        
        Visit visit = new Visit();
        visit.setId(entity.getId());
        visit.setDate(entity.getDate());
        visit.setDescription(entity.getDescription());
        
        return visit;
    }
}