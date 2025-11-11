package com.petclinic.domain.port;

import com.petclinic.domain.model.PetType;
import java.util.List;
import java.util.Optional;

/**
 * Port interface for PetType repository operations
 */
public interface PetTypeRepositoryPort {
    
    /**
     * Save a pet type
     */
    PetType save(PetType petType);
    
    /**
     * Find pet type by ID
     */
    Optional<PetType> findById(Long id);
    
    /**
     * Find all pet types
     */
    List<PetType> findAll();
    
    /**
     * Find pet type by name
     */
    Optional<PetType> findByName(String name);
    
    /**
     * Delete pet type by ID
     */
    void deleteById(Long id);
    
    /**
     * Check if pet type exists by ID
     */
    boolean existsById(Long id);
}