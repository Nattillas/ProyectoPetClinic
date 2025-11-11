package com.petclinic.infrastructure.web.mapper;

import com.petclinic.domain.model.PetType;
import com.petclinic.infrastructure.web.dto.PetTypeDto;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between PetType domain objects and DTOs.
 */
@Component
public class PetTypeMapper {

    /**
     * Convert PetType domain object to DTO.
     *
     * @param petType Domain object
     * @return DTO
     */
    public PetTypeDto toDto(PetType petType) {
        if (petType == null) {
            return null;
        }
        
        return new PetTypeDto(
            petType.getId(),
            petType.getName()
        );
    }

    /**
     * Convert PetTypeDto to domain object.
     *
     * @param dto DTO
     * @return Domain object
     */
    public PetType toDomain(PetTypeDto dto) {
        if (dto == null) {
            return null;
        }
        
        PetType petType = new PetType();
        petType.setId(dto.getId());
        petType.setName(dto.getName());
        
        return petType;
    }
}