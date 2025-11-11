package com.petclinic.application.service;

import com.petclinic.domain.model.PetType;
import com.petclinic.domain.port.PetTypeRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing PetType entities.
 * Implements business logic for pet type operations.
 */
@Service
@Transactional
public class PetTypeService {

    private final PetTypeRepositoryPort petTypeRepository;

    public PetTypeService(PetTypeRepositoryPort petTypeRepository) {
        this.petTypeRepository = petTypeRepository;
    }

    /**
     * Retrieve all pet types.
     *
     * @return List of all pet types
     */
    @Transactional(readOnly = true)
    public List<PetType> findAll() {
        return petTypeRepository.findAll();
    }

    /**
     * Find pet type by ID.
     *
     * @param id Pet type ID
     * @return Optional containing the pet type if found
     */
    @Transactional(readOnly = true)
    public Optional<PetType> findById(Long id) {
        return petTypeRepository.findById(id);
    }

    /**
     * Find pet type by name.
     *
     * @param name Pet type name
     * @return Optional containing the pet type if found
     */
    @Transactional(readOnly = true)
    public Optional<PetType> findByName(String name) {
        return petTypeRepository.findByName(name);
    }

    /**
     * Create a new pet type.
     *
     * @param petType Pet type to create
     * @return Created pet type
     */
    public PetType save(PetType petType) {
        validatePetType(petType);
        return petTypeRepository.save(petType);
    }

    /**
     * Update an existing pet type.
     *
     * @param id Pet type ID
     * @param petType Updated pet type data
     * @return Updated pet type
     * @throws RuntimeException if pet type not found
     */
    public PetType update(Long id, PetType petType) {
        validatePetType(petType);
        
        Optional<PetType> existingPetType = petTypeRepository.findById(id);
        if (existingPetType.isEmpty()) {
            throw new RuntimeException("Pet type not found with ID: " + id);
        }

        PetType updated = existingPetType.get();
        updated.setName(petType.getName());
        
        return petTypeRepository.save(updated);
    }

    /**
     * Delete pet type by ID.
     *
     * @param id Pet type ID
     * @throws RuntimeException if pet type not found
     */
    public void deleteById(Long id) {
        Optional<PetType> existingPetType = petTypeRepository.findById(id);
        if (existingPetType.isEmpty()) {
            throw new RuntimeException("Pet type not found with ID: " + id);
        }
        
        petTypeRepository.deleteById(id);
    }

    /**
     * Validate pet type data.
     *
     * @param petType Pet type to validate
     * @throws RuntimeException if validation fails
     */
    private void validatePetType(PetType petType) {
        if (petType.getName() == null || petType.getName().trim().isEmpty()) {
            throw new RuntimeException("Pet type name is required");
        }
        
        if (petType.getName().length() > 50) {
            throw new RuntimeException("Pet type name cannot exceed 50 characters");
        }
    }
}