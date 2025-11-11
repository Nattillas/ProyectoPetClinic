package com.petclinic.application.mapper;

import com.petclinic.application.dto.PetDto;
import com.petclinic.application.dto.PetTypeDto;
import com.petclinic.application.dto.OwnerDto;
import com.petclinic.domain.model.Pet;
import com.petclinic.domain.model.PetType;
import com.petclinic.domain.model.Owner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper for converting between Pet DTOs and domain models
 */
@Component
public class PetMapper {
    
    public PetDto toDto(Pet pet) {
        if (pet == null) {
            return null;
        }
        
        PetDto dto = new PetDto();
        dto.setId(pet.getId());
        dto.setName(pet.getName());
        dto.setBirthDate(pet.getBirthDate());
        
        // Map pet type
        if (pet.getType() != null) {
            PetTypeDto typeDto = new PetTypeDto();
            typeDto.setId(pet.getType().getId());
            typeDto.setName(pet.getType().getName());
            dto.setType(typeDto);
        }
        
        // Map owner
        if (pet.getOwner() != null) {
            OwnerDto ownerDto = new OwnerDto();
            ownerDto.setId(pet.getOwner().getId());
            ownerDto.setFirstName(pet.getOwner().getFirstName());
            ownerDto.setLastName(pet.getOwner().getLastName());
            ownerDto.setAddress(pet.getOwner().getAddress());
            ownerDto.setCity(pet.getOwner().getCity());
            ownerDto.setTelephone(pet.getOwner().getTelephone());
            dto.setOwner(ownerDto);
        }
        
        return dto;
    }
    
    public Pet toDomain(PetDto dto) {
        if (dto == null) {
            return null;
        }
        
        Pet pet = new Pet();
        pet.setId(dto.getId());
        pet.setName(dto.getName());
        pet.setBirthDate(dto.getBirthDate());
        
        // Map pet type
        if (dto.getType() != null) {
            PetType petType = new PetType();
            petType.setId(dto.getType().getId());
            petType.setName(dto.getType().getName());
            pet.setType(petType);
        }
        
        // Map owner
        if (dto.getOwner() != null) {
            Owner owner = new Owner();
            owner.setId(dto.getOwner().getId());
            owner.setFirstName(dto.getOwner().getFirstName());
            owner.setLastName(dto.getOwner().getLastName());
            owner.setAddress(dto.getOwner().getAddress());
            owner.setCity(dto.getOwner().getCity());
            owner.setTelephone(dto.getOwner().getTelephone());
            pet.setOwner(owner);
        }
        
        return pet;
    }
    
    public List<PetDto> toDtoList(List<Pet> pets) {
        if (pets == null) {
            return null;
        }
        return pets.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    
    public List<Pet> toDomainList(List<PetDto> dtos) {
        if (dtos == null) {
            return null;
        }
        return dtos.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }
}