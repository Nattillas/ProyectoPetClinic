package com.petclinic.infrastructure.adapter;

import com.petclinic.domain.model.PetType;
import com.petclinic.domain.port.PetTypeRepositoryPort;
import com.petclinic.infrastructure.entity.PetTypeEntity;
import com.petclinic.infrastructure.repository.PetTypeJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adapter that implements PetTypeRepositoryPort using JPA
 */
@Component
public class PetTypeRepositoryAdapter implements PetTypeRepositoryPort {
    
    private final PetTypeJpaRepository petTypeJpaRepository;
    
    public PetTypeRepositoryAdapter(PetTypeJpaRepository petTypeJpaRepository) {
        this.petTypeJpaRepository = petTypeJpaRepository;
    }
    
    @Override
    public PetType save(PetType petType) {
        PetTypeEntity entity = toEntity(petType);
        PetTypeEntity savedEntity = petTypeJpaRepository.save(entity);
        return toDomain(savedEntity);
    }
    
    @Override
    public Optional<PetType> findById(Long id) {
        Optional<PetTypeEntity> entity = petTypeJpaRepository.findById(id);
        return entity.map(this::toDomain);
    }
    
    @Override
    public List<PetType> findAll() {
        return petTypeJpaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<PetType> findByName(String name) {
        Optional<PetTypeEntity> entity = petTypeJpaRepository.findByName(name);
        return entity.map(this::toDomain);
    }
    
    @Override
    public void deleteById(Long id) {
        petTypeJpaRepository.deleteById(id);
    }
    
    @Override
    public boolean existsById(Long id) {
        return petTypeJpaRepository.existsById(id);
    }
    
    private PetType toDomain(PetTypeEntity entity) {
        if (entity == null) {
            return null;
        }
        
        PetType petType = new PetType();
        petType.setId(entity.getId());
        petType.setName(entity.getName());
        
        return petType;
    }
    
    private PetTypeEntity toEntity(PetType petType) {
        if (petType == null) {
            return null;
        }
        
        PetTypeEntity entity = new PetTypeEntity();
        entity.setId(petType.getId());
        entity.setName(petType.getName());
        
        return entity;
    }
}