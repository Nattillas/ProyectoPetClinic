package com.petclinic.infrastructure.repository;

import com.petclinic.infrastructure.entity.PetTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPA Repository for PetType entities
 */
@Repository
public interface PetTypeJpaRepository extends JpaRepository<PetTypeEntity, Long> {
    
    /**
     * Find pet type by name
     */
    Optional<PetTypeEntity> findByName(String name);
}