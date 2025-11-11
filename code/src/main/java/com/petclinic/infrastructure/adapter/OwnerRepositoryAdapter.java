package com.petclinic.infrastructure.adapter;

import com.petclinic.domain.model.Owner;
import com.petclinic.domain.port.OwnerRepositoryPort;
import com.petclinic.infrastructure.entity.OwnerEntity;
import com.petclinic.infrastructure.repository.OwnerJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adapter that implements OwnerRepositoryPort using JPA
 */
@Component
public class OwnerRepositoryAdapter implements OwnerRepositoryPort {
    
    private final OwnerJpaRepository ownerJpaRepository;
    private final OwnerEntityMapper ownerEntityMapper;
    
    public OwnerRepositoryAdapter(OwnerJpaRepository ownerJpaRepository, OwnerEntityMapper ownerEntityMapper) {
        this.ownerJpaRepository = ownerJpaRepository;
        this.ownerEntityMapper = ownerEntityMapper;
    }
    
    @Override
    public Owner save(Owner owner) {
        OwnerEntity entity = ownerEntityMapper.toEntity(owner);
        OwnerEntity savedEntity = ownerJpaRepository.save(entity);
        return ownerEntityMapper.toDomain(savedEntity);
    }
    
    @Override
    public Optional<Owner> findById(Long id) {
        Optional<OwnerEntity> entity = ownerJpaRepository.findById(id);
        return entity.map(ownerEntityMapper::toDomain);
    }
    
    @Override
    public List<Owner> findAll() {
        return ownerJpaRepository.findAll().stream()
                .map(ownerEntityMapper::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Owner> findByLastNameContainingIgnoreCase(String lastName) {
        return ownerJpaRepository.findByLastNameContainingIgnoreCase(lastName).stream()
                .map(ownerEntityMapper::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Owner> findByFirstNameAndLastName(String firstName, String lastName) {
        return ownerJpaRepository.findByFirstNameAndLastName(firstName, lastName).stream()
                .map(ownerEntityMapper::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public void deleteById(Long id) {
        ownerJpaRepository.deleteById(id);
    }
    
    @Override
    public boolean existsById(Long id) {
        return ownerJpaRepository.existsById(id);
    }
}