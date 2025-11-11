package com.petclinic.infrastructure.adapter;

import com.petclinic.domain.model.Pet;
import com.petclinic.domain.port.PetRepositoryPort;
import com.petclinic.infrastructure.entity.PetEntity;
import com.petclinic.infrastructure.repository.PetJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adapter that implements PetRepositoryPort using JPA
 * This is part of the infrastructure layer and translates between domain models and JPA entities
 */
@Component
public class PetRepositoryAdapter implements PetRepositoryPort {
    
    private final PetJpaRepository petJpaRepository;
    private final PetEntityMapper petEntityMapper;
    
    public PetRepositoryAdapter(PetJpaRepository petJpaRepository, PetEntityMapper petEntityMapper) {
        this.petJpaRepository = petJpaRepository;
        this.petEntityMapper = petEntityMapper;
    }
    
    @Override
    public Pet save(Pet pet) {
        PetEntity entity = petEntityMapper.toEntity(pet);
        PetEntity savedEntity = petJpaRepository.save(entity);
        return petEntityMapper.toDomain(savedEntity);
    }
    
    @Override
    public Optional<Pet> findById(Long id) {
        Optional<PetEntity> entity = petJpaRepository.findById(id);
        return entity.map(petEntityMapper::toDomain);
    }
    
    @Override
    public List<Pet> findAll() {
        return petJpaRepository.findAll().stream()
                .map(petEntityMapper::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Pet> findByOwnerId(Long ownerId) {
        return petJpaRepository.findByOwnerId(ownerId).stream()
                .map(petEntityMapper::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Pet> findByNameContainingIgnoreCase(String name) {
        return petJpaRepository.findByNameContainingIgnoreCase(name).stream()
                .map(petEntityMapper::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public void deleteById(Long id) {
        petJpaRepository.deleteById(id);
    }
    
    @Override
    public boolean existsById(Long id) {
        return petJpaRepository.existsById(id);
    }
}