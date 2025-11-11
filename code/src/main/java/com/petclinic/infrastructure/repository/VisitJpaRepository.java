package com.petclinic.infrastructure.repository;

import com.petclinic.infrastructure.entity.VisitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA Repository for Visit entities
 */
@Repository
public interface VisitJpaRepository extends JpaRepository<VisitEntity, Long> {
    
    /**
     * Find visits by pet ID
     */
    List<VisitEntity> findByPetId(Long petId);
}