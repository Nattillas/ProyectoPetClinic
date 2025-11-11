package com.petclinic.domain.port;

import com.petclinic.domain.model.Pet;
import java.util.List;
import java.util.Optional;

/**
 * Port interface for Pet repository operations
 * This follows the hexagonal architecture pattern where the domain defines
 * the contract for persistence operations without depending on implementation details
 */
public interface PetRepositoryPort {
    
    /**
     * Save a pet
     */
    Pet save(Pet pet);
    
    /**
     * Find pet by ID
     */
    Optional<Pet> findById(Long id);
    
    /**
     * Find all pets
     */
    List<Pet> findAll();
    
    /**
     * Find pets by owner ID
     */
    List<Pet> findByOwnerId(Long ownerId);
    
    /**
     * Find pets by name containing (case insensitive)
     */
    List<Pet> findByNameContainingIgnoreCase(String name);
    
    /**
     * Delete pet by ID
     */
    void deleteById(Long id);
    
    /**
     * Check if pet exists by ID
     */
    boolean existsById(Long id);
}