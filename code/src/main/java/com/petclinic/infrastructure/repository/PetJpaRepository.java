package com.petclinic.infrastructure.repository;

import com.petclinic.infrastructure.entity.PetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA Repository for Pet entities
 */
@Repository
public interface PetJpaRepository extends JpaRepository<PetEntity, Long> {
    
    /**
     * Find pets by owner ID
     */
    List<PetEntity> findByOwnerId(Long ownerId);
    
    /**
     * Find pets by name containing (case insensitive)
     */
    List<PetEntity> findByNameContainingIgnoreCase(String name);
    
    /**
     * Find pets with their owner and type information
     */
    @Query("SELECT p FROM PetEntity p JOIN FETCH p.owner JOIN FETCH p.type WHERE p.id = :id")
    PetEntity findByIdWithDetails(@Param("id") Long id);
}