package com.petclinic.domain.service;

import com.petclinic.domain.model.Pet;
import com.petclinic.domain.port.PetRepositoryPort;
import com.petclinic.domain.port.OwnerRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Pet domain service implementing business logic
 * This service contains the core business rules and orchestrates operations
 */
@Service
public class PetService {
    
    private final PetRepositoryPort petRepository;
    private final OwnerRepositoryPort ownerRepository;
    
    public PetService(PetRepositoryPort petRepository, OwnerRepositoryPort ownerRepository) {
        this.petRepository = petRepository;
        this.ownerRepository = ownerRepository;
    }
    
    /**
     * Create a new pet
     */
    public Pet createPet(Pet pet) {
        validatePet(pet);
        return petRepository.save(pet);
    }
    
    /**
     * Update an existing pet
     */
    public Pet updatePet(Long id, Pet petUpdates) {
        Optional<Pet> existingPet = petRepository.findById(id);
        if (existingPet.isEmpty()) {
            throw new PetNotFoundException("Pet with id " + id + " not found");
        }
        
        Pet pet = existingPet.get();
        updatePetFields(pet, petUpdates);
        validatePet(pet);
        
        return petRepository.save(pet);
    }
    
    /**
     * Find pet by ID
     */
    public Optional<Pet> findPetById(Long id) {
        return petRepository.findById(id);
    }
    
    /**
     * Find all pets
     */
    public List<Pet> findAllPets() {
        return petRepository.findAll();
    }
    
    /**
     * Find pets by owner ID
     */
    public List<Pet> findPetsByOwnerId(Long ownerId) {
        if (!ownerRepository.existsById(ownerId)) {
            throw new OwnerNotFoundException("Owner with id " + ownerId + " not found");
        }
        return petRepository.findByOwnerId(ownerId);
    }
    
    /**
     * Search pets by name
     */
    public List<Pet> searchPetsByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return findAllPets();
        }
        return petRepository.findByNameContainingIgnoreCase(name.trim());
    }
    
    /**
     * Delete pet by ID
     */
    public void deletePet(Long id) {
        if (!petRepository.existsById(id)) {
            throw new PetNotFoundException("Pet with id " + id + " not found");
        }
        petRepository.deleteById(id);
    }
    
    /**
     * Check if pet exists
     */
    public boolean petExists(Long id) {
        return petRepository.existsById(id);
    }
    
    private void validatePet(Pet pet) {
        if (pet.getName() == null || pet.getName().trim().isEmpty()) {
            throw new InvalidPetException("Pet name cannot be null or empty");
        }
        
        if (pet.getType() == null) {
            throw new InvalidPetException("Pet type cannot be null");
        }
        
        if (pet.getBirthDate() == null) {
            throw new InvalidPetException("Pet birth date cannot be null");
        }
    }
    
    private void updatePetFields(Pet existing, Pet updates) {
        if (updates.getName() != null) {
            existing.setName(updates.getName());
        }
        if (updates.getBirthDate() != null) {
            existing.setBirthDate(updates.getBirthDate());
        }
        if (updates.getType() != null) {
            existing.setType(updates.getType());
        }
    }
    
    // Custom exceptions
    public static class PetNotFoundException extends RuntimeException {
        public PetNotFoundException(String message) {
            super(message);
        }
    }
    
    public static class InvalidPetException extends RuntimeException {
        public InvalidPetException(String message) {
            super(message);
        }
    }
    
    public static class OwnerNotFoundException extends RuntimeException {
        public OwnerNotFoundException(String message) {
            super(message);
        }
    }
}