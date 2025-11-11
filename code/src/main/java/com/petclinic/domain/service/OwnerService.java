package com.petclinic.domain.service;

import com.petclinic.domain.model.Owner;
import com.petclinic.domain.port.OwnerRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Owner domain service implementing business logic
 */
@Service
public class OwnerService {
    
    private final OwnerRepositoryPort ownerRepository;
    
    public OwnerService(OwnerRepositoryPort ownerRepository) {
        this.ownerRepository = ownerRepository;
    }
    
    /**
     * Create a new owner
     */
    public Owner createOwner(Owner owner) {
        validateOwner(owner);
        return ownerRepository.save(owner);
    }
    
    /**
     * Update an existing owner
     */
    public Owner updateOwner(Long id, Owner ownerUpdates) {
        Optional<Owner> existingOwner = ownerRepository.findById(id);
        if (existingOwner.isEmpty()) {
            throw new OwnerNotFoundException("Owner with id " + id + " not found");
        }
        
        Owner owner = existingOwner.get();
        updateOwnerFields(owner, ownerUpdates);
        validateOwner(owner);
        
        return ownerRepository.save(owner);
    }
    
    /**
     * Find owner by ID
     */
    public Optional<Owner> findOwnerById(Long id) {
        return ownerRepository.findById(id);
    }
    
    /**
     * Find all owners
     */
    public List<Owner> findAllOwners() {
        return ownerRepository.findAll();
    }
    
    /**
     * Search owners by last name
     */
    public List<Owner> searchOwnersByLastName(String lastName) {
        if (lastName == null || lastName.trim().isEmpty()) {
            return findAllOwners();
        }
        return ownerRepository.findByLastNameContainingIgnoreCase(lastName.trim());
    }
    
    /**
     * Find owners by full name
     */
    public List<Owner> findOwnersByFullName(String firstName, String lastName) {
        return ownerRepository.findByFirstNameAndLastName(firstName, lastName);
    }
    
    /**
     * Delete owner by ID
     */
    public void deleteOwner(Long id) {
        Optional<Owner> owner = ownerRepository.findById(id);
        if (owner.isEmpty()) {
            throw new OwnerNotFoundException("Owner with id " + id + " not found");
        }
        
        // Check if owner has pets - in a real scenario, you might want to handle this differently
        if (!owner.get().getPets().isEmpty()) {
            throw new OwnerHasPetsException("Cannot delete owner with pets. Please remove pets first.");
        }
        
        ownerRepository.deleteById(id);
    }
    
    /**
     * Check if owner exists
     */
    public boolean ownerExists(Long id) {
        return ownerRepository.existsById(id);
    }
    
    private void validateOwner(Owner owner) {
        if (owner.getFirstName() == null || owner.getFirstName().trim().isEmpty()) {
            throw new InvalidOwnerException("Owner first name cannot be null or empty");
        }
        
        if (owner.getLastName() == null || owner.getLastName().trim().isEmpty()) {
            throw new InvalidOwnerException("Owner last name cannot be null or empty");
        }
        
        if (owner.getTelephone() != null && !isValidTelephone(owner.getTelephone())) {
            throw new InvalidOwnerException("Invalid telephone format");
        }
    }
    
    private boolean isValidTelephone(String telephone) {
        // Simple validation - numbers and common separators
        return telephone.matches("^[0-9\\s\\-\\+\\(\\)]+$");
    }
    
    private void updateOwnerFields(Owner existing, Owner updates) {
        if (updates.getFirstName() != null) {
            existing.setFirstName(updates.getFirstName());
        }
        if (updates.getLastName() != null) {
            existing.setLastName(updates.getLastName());
        }
        if (updates.getAddress() != null) {
            existing.setAddress(updates.getAddress());
        }
        if (updates.getCity() != null) {
            existing.setCity(updates.getCity());
        }
        if (updates.getTelephone() != null) {
            existing.setTelephone(updates.getTelephone());
        }
    }
    
    // Custom exceptions
    public static class OwnerNotFoundException extends RuntimeException {
        public OwnerNotFoundException(String message) {
            super(message);
        }
    }
    
    public static class InvalidOwnerException extends RuntimeException {
        public InvalidOwnerException(String message) {
            super(message);
        }
    }
    
    public static class OwnerHasPetsException extends RuntimeException {
        public OwnerHasPetsException(String message) {
            super(message);
        }
    }
}