package com.petclinic.infrastructure.repository;

import com.petclinic.infrastructure.entity.OwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA Repository for Owner entities
 */
@Repository
public interface OwnerJpaRepository extends JpaRepository<OwnerEntity, Long> {
    
    /**
     * Find owners by last name containing (case insensitive)
     */
    List<OwnerEntity> findByLastNameContainingIgnoreCase(String lastName);
    
    /**
     * Find owners by first name and last name
     */
    List<OwnerEntity> findByFirstNameAndLastName(String firstName, String lastName);
    
    /**
     * Find owners with their pets
     */
    @Query("SELECT DISTINCT o FROM OwnerEntity o LEFT JOIN FETCH o.pets")
    List<OwnerEntity> findAllWithPets();
}