package com.petclinic.domain.port;

import com.petclinic.domain.model.Owner;
import java.util.List;
import java.util.Optional;

/**
 * Port interface for Owner repository operations
 */
public interface OwnerRepositoryPort {
    
    /**
     * Save an owner
     */
    Owner save(Owner owner);
    
    /**
     * Find owner by ID
     */
    Optional<Owner> findById(Long id);
    
    /**
     * Find all owners
     */
    List<Owner> findAll();
    
    /**
     * Find owners by last name containing (case insensitive)
     */
    List<Owner> findByLastNameContainingIgnoreCase(String lastName);
    
    /**
     * Find owners by first name and last name
     */
    List<Owner> findByFirstNameAndLastName(String firstName, String lastName);
    
    /**
     * Delete owner by ID
     */
    void deleteById(Long id);
    
    /**
     * Check if owner exists by ID
     */
    boolean existsById(Long id);
}