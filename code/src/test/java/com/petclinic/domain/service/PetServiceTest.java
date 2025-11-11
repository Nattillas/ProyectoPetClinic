package com.petclinic.domain.service;

import com.petclinic.domain.model.Pet;
import com.petclinic.domain.model.PetType;
import com.petclinic.domain.model.Owner;
import com.petclinic.domain.port.PetRepositoryPort;
import com.petclinic.domain.port.OwnerRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for PetService
 * Tests the business logic in isolation using Mockito
 */
@ExtendWith(MockitoExtension.class)
class PetServiceTest {
    
    @Mock
    private PetRepositoryPort petRepositoryPort;
    
    @Mock
    private OwnerRepositoryPort ownerRepositoryPort;
    
    private PetService petService;
    
    private Pet testPet;
    private PetType testPetType;
    private Owner testOwner;
    
    @BeforeEach
    void setUp() {
        petService = new PetService(petRepositoryPort, ownerRepositoryPort);
        
        testPetType = new PetType("dog");
        testPetType.setId(1L);
        
        testOwner = new Owner("John", "Doe");
        testOwner.setId(1L);
        
        testPet = new Pet("Buddy", LocalDate.of(2020, 1, 1), testPetType);
        testPet.setId(1L);
        testPet.setOwner(testOwner);
    }
    
    @Test
    void createPet_WithValidPet_ShouldReturnSavedPet() {
        // Given
        when(petRepositoryPort.save(any(Pet.class))).thenReturn(testPet);
        
        // When
        Pet result = petService.createPet(testPet);
        
        // Then
        assertNotNull(result);
        assertEquals(testPet.getName(), result.getName());
        assertEquals(testPet.getBirthDate(), result.getBirthDate());
        verify(petRepositoryPort).save(testPet);
    }
    
    @Test
    void createPet_WithNullName_ShouldThrowException() {
        // Given
        testPet.setName(null);
        
        // When & Then
        assertThrows(PetService.InvalidPetException.class, () -> {
            petService.createPet(testPet);
        });
        
        verify(petRepositoryPort, never()).save(any(Pet.class));
    }
    
    @Test
    void createPet_WithEmptyName_ShouldThrowException() {
        // Given
        testPet.setName("");
        
        // When & Then
        assertThrows(PetService.InvalidPetException.class, () -> {
            petService.createPet(testPet);
        });
        
        verify(petRepositoryPort, never()).save(any(Pet.class));
    }
    
    @Test
    void createPet_WithNullType_ShouldThrowException() {
        // Given
        testPet.setType(null);
        
        // When & Then
        assertThrows(PetService.InvalidPetException.class, () -> {
            petService.createPet(testPet);
        });
        
        verify(petRepositoryPort, never()).save(any(Pet.class));
    }
    
    @Test
    void createPet_WithNullBirthDate_ShouldThrowException() {
        // Given
        testPet.setBirthDate(null);
        
        // When & Then
        assertThrows(PetService.InvalidPetException.class, () -> {
            petService.createPet(testPet);
        });
        
        verify(petRepositoryPort, never()).save(any(Pet.class));
    }
    
    @Test
    void findPetById_WithExistingId_ShouldReturnPet() {
        // Given
        Long petId = 1L;
        when(petRepositoryPort.findById(petId)).thenReturn(Optional.of(testPet));
        
        // When
        Optional<Pet> result = petService.findPetById(petId);
        
        // Then
        assertTrue(result.isPresent());
        assertEquals(testPet.getName(), result.get().getName());
        verify(petRepositoryPort).findById(petId);
    }
    
    @Test
    void findPetById_WithNonExistingId_ShouldReturnEmpty() {
        // Given
        Long petId = 999L;
        when(petRepositoryPort.findById(petId)).thenReturn(Optional.empty());
        
        // When
        Optional<Pet> result = petService.findPetById(petId);
        
        // Then
        assertFalse(result.isPresent());
        verify(petRepositoryPort).findById(petId);
    }
    
    @Test
    void findAllPets_ShouldReturnAllPets() {
        // Given
        Pet secondPet = new Pet("Rex", LocalDate.of(2019, 5, 15), testPetType);
        List<Pet> pets = Arrays.asList(testPet, secondPet);
        when(petRepositoryPort.findAll()).thenReturn(pets);
        
        // When
        List<Pet> result = petService.findAllPets();
        
        // Then
        assertEquals(2, result.size());
        assertEquals(pets, result);
        verify(petRepositoryPort).findAll();
    }
    
    @Test
    void findPetsByOwnerId_WithExistingOwner_ShouldReturnPets() {
        // Given
        Long ownerId = 1L;
        List<Pet> pets = Arrays.asList(testPet);
        when(ownerRepositoryPort.existsById(ownerId)).thenReturn(true);
        when(petRepositoryPort.findByOwnerId(ownerId)).thenReturn(pets);
        
        // When
        List<Pet> result = petService.findPetsByOwnerId(ownerId);
        
        // Then
        assertEquals(1, result.size());
        assertEquals(testPet.getName(), result.get(0).getName());
        verify(ownerRepositoryPort).existsById(ownerId);
        verify(petRepositoryPort).findByOwnerId(ownerId);
    }
    
    @Test
    void findPetsByOwnerId_WithNonExistingOwner_ShouldThrowException() {
        // Given
        Long ownerId = 999L;
        when(ownerRepositoryPort.existsById(ownerId)).thenReturn(false);
        
        // When & Then
        assertThrows(PetService.OwnerNotFoundException.class, () -> {
            petService.findPetsByOwnerId(ownerId);
        });
        
        verify(ownerRepositoryPort).existsById(ownerId);
        verify(petRepositoryPort, never()).findByOwnerId(any());
    }
    
    @Test
    void searchPetsByName_WithValidName_ShouldReturnMatchingPets() {
        // Given
        String searchName = "Bud";
        List<Pet> pets = Arrays.asList(testPet);
        when(petRepositoryPort.findByNameContainingIgnoreCase(searchName)).thenReturn(pets);
        
        // When
        List<Pet> result = petService.searchPetsByName(searchName);
        
        // Then
        assertEquals(1, result.size());
        assertEquals(testPet.getName(), result.get(0).getName());
        verify(petRepositoryPort).findByNameContainingIgnoreCase(searchName);
    }
    
    @Test
    void searchPetsByName_WithNullName_ShouldReturnAllPets() {
        // Given
        List<Pet> allPets = Arrays.asList(testPet);
        when(petRepositoryPort.findAll()).thenReturn(allPets);
        
        // When
        List<Pet> result = petService.searchPetsByName(null);
        
        // Then
        assertEquals(1, result.size());
        verify(petRepositoryPort).findAll();
        verify(petRepositoryPort, never()).findByNameContainingIgnoreCase(any());
    }
    
    @Test
    void searchPetsByName_WithEmptyName_ShouldReturnAllPets() {
        // Given
        List<Pet> allPets = Arrays.asList(testPet);
        when(petRepositoryPort.findAll()).thenReturn(allPets);
        
        // When
        List<Pet> result = petService.searchPetsByName("");
        
        // Then
        assertEquals(1, result.size());
        verify(petRepositoryPort).findAll();
        verify(petRepositoryPort, never()).findByNameContainingIgnoreCase(any());
    }
    
    @Test
    void updatePet_WithValidData_ShouldReturnUpdatedPet() {
        // Given
        Long petId = 1L;
        Pet updatedPet = new Pet("UpdatedBuddy", LocalDate.of(2020, 1, 1), testPetType);
        
        when(petRepositoryPort.findById(petId)).thenReturn(Optional.of(testPet));
        when(petRepositoryPort.save(any(Pet.class))).thenReturn(testPet);
        
        // When
        Pet result = petService.updatePet(petId, updatedPet);
        
        // Then
        assertNotNull(result);
        verify(petRepositoryPort).findById(petId);
        verify(petRepositoryPort).save(any(Pet.class));
    }
    
    @Test
    void updatePet_WithNonExistingId_ShouldThrowException() {
        // Given
        Long petId = 999L;
        Pet updatedPet = new Pet("UpdatedBuddy", LocalDate.of(2020, 1, 1), testPetType);
        
        when(petRepositoryPort.findById(petId)).thenReturn(Optional.empty());
        
        // When & Then
        assertThrows(PetService.PetNotFoundException.class, () -> {
            petService.updatePet(petId, updatedPet);
        });
        
        verify(petRepositoryPort).findById(petId);
        verify(petRepositoryPort, never()).save(any(Pet.class));
    }
    
    @Test
    void deletePet_WithExistingId_ShouldDeletePet() {
        // Given
        Long petId = 1L;
        when(petRepositoryPort.existsById(petId)).thenReturn(true);
        
        // When
        petService.deletePet(petId);
        
        // Then
        verify(petRepositoryPort).existsById(petId);
        verify(petRepositoryPort).deleteById(petId);
    }
    
    @Test
    void deletePet_WithNonExistingId_ShouldThrowException() {
        // Given
        Long petId = 999L;
        when(petRepositoryPort.existsById(petId)).thenReturn(false);
        
        // When & Then
        assertThrows(PetService.PetNotFoundException.class, () -> {
            petService.deletePet(petId);
        });
        
        verify(petRepositoryPort).existsById(petId);
        verify(petRepositoryPort, never()).deleteById(any());
    }
    
    @Test
    void petExists_WithExistingId_ShouldReturnTrue() {
        // Given
        Long petId = 1L;
        when(petRepositoryPort.existsById(petId)).thenReturn(true);
        
        // When
        boolean result = petService.petExists(petId);
        
        // Then
        assertTrue(result);
        verify(petRepositoryPort).existsById(petId);
    }
    
    @Test
    void petExists_WithNonExistingId_ShouldReturnFalse() {
        // Given
        Long petId = 999L;
        when(petRepositoryPort.existsById(petId)).thenReturn(false);
        
        // When
        boolean result = petService.petExists(petId);
        
        // Then
        assertFalse(result);
        verify(petRepositoryPort).existsById(petId);
    }
}