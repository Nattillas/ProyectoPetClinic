package com.petclinic.domain.port;

import com.petclinic.domain.model.Visit;
import java.util.List;
import java.util.Optional;

/**
 * Port interface for Visit repository operations
 */
public interface VisitRepositoryPort {
    
    /**
     * Save a visit
     */
    Visit save(Visit visit);
    
    /**
     * Find visit by ID
     */
    Optional<Visit> findById(Long id);
    
    /**
     * Find all visits
     */
    List<Visit> findAll();
    
    /**
     * Find visits by pet ID
     */
    List<Visit> findByPetId(Long petId);
    
    /**
     * Delete visit by ID
     */
    void deleteById(Long id);
    
    /**
     * Check if visit exists by ID
     */
    boolean existsById(Long id);
}