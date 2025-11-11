package com.petclinic.application.controller;

import com.petclinic.application.dto.PetDto;
import com.petclinic.application.dto.PetTypeDto;
import com.petclinic.application.dto.OwnerDto;
import com.petclinic.application.mapper.PetMapper;
import com.petclinic.domain.model.Pet;
import com.petclinic.domain.model.PetType;
import com.petclinic.domain.model.Owner;
import com.petclinic.domain.service.PetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for PetController
 * Tests the REST API layer using MockMvc
 */
@WebMvcTest(PetController.class)
class PetControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private PetService petService;
    
    @MockBean
    private PetMapper petMapper;
    
    private ObjectMapper objectMapper;
    private Pet testPet;
    private PetDto testPetDto;
    private PetType testPetType;
    private PetTypeDto testPetTypeDto;
    private Owner testOwner;
    private OwnerDto testOwnerDto;
    
    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        
        testPetType = new PetType("dog");
        testPetType.setId(1L);
        
        testPetTypeDto = new PetTypeDto("dog");
        testPetTypeDto.setId(1L);
        
        testOwner = new Owner("John", "Doe");
        testOwner.setId(1L);
        
        testOwnerDto = new OwnerDto("John", "Doe");
        testOwnerDto.setId(1L);
        
        testPet = new Pet("Buddy", LocalDate.of(2020, 1, 1), testPetType);
        testPet.setId(1L);
        testPet.setOwner(testOwner);
        
        testPetDto = new PetDto("Buddy", LocalDate.of(2020, 1, 1), testPetTypeDto);
        testPetDto.setId(1L);
        testPetDto.setOwner(testOwnerDto);
    }
    
    @Test
    void getAllPets_ShouldReturnListOfPets() throws Exception {
        // Given
        List<Pet> pets = Arrays.asList(testPet);
        List<PetDto> petDtos = Arrays.asList(testPetDto);
        
        when(petService.findAllPets()).thenReturn(pets);
        when(petMapper.toDtoList(pets)).thenReturn(petDtos);
        
        // When & Then
        mockMvc.perform(get("/api/pets"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Buddy"));
    }
    
    @Test
    void getPetById_WithExistingId_ShouldReturnPet() throws Exception {
        // Given
        Long petId = 1L;
        when(petService.findPetById(petId)).thenReturn(Optional.of(testPet));
        when(petMapper.toDto(testPet)).thenReturn(testPetDto);
        
        // When & Then
        mockMvc.perform(get("/api/pets/{id}", petId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Buddy"));
    }
    
    @Test
    void getPetById_WithNonExistingId_ShouldReturnNotFound() throws Exception {
        // Given
        Long petId = 999L;
        when(petService.findPetById(petId)).thenReturn(Optional.empty());
        
        // When & Then
        mockMvc.perform(get("/api/pets/{id}", petId))
                .andExpect(status().isNotFound());
    }
    
    @Test
    void createPet_WithValidData_ShouldReturnCreatedPet() throws Exception {
        // Given
        when(petMapper.toDomain(any(PetDto.class))).thenReturn(testPet);
        when(petService.createPet(any(Pet.class))).thenReturn(testPet);
        when(petMapper.toDto(testPet)).thenReturn(testPetDto);
        
        // When & Then
        mockMvc.perform(post("/api/pets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testPetDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Buddy"));
    }
    
    @Test
    void createPet_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        // Given
        when(petMapper.toDomain(any(PetDto.class))).thenReturn(testPet);
        when(petService.createPet(any(Pet.class)))
                .thenThrow(new PetService.InvalidPetException("Invalid pet data"));
        
        // When & Then
        mockMvc.perform(post("/api/pets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testPetDto)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void updatePet_WithValidData_ShouldReturnUpdatedPet() throws Exception {
        // Given
        Long petId = 1L;
        when(petMapper.toDomain(any(PetDto.class))).thenReturn(testPet);
        when(petService.updatePet(eq(petId), any(Pet.class))).thenReturn(testPet);
        when(petMapper.toDto(testPet)).thenReturn(testPetDto);
        
        // When & Then
        mockMvc.perform(put("/api/pets/{id}", petId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testPetDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Buddy"));
    }
    
    @Test
    void updatePet_WithNonExistingId_ShouldReturnNotFound() throws Exception {
        // Given
        Long petId = 999L;
        when(petMapper.toDomain(any(PetDto.class))).thenReturn(testPet);
        when(petService.updatePet(eq(petId), any(Pet.class)))
                .thenThrow(new PetService.PetNotFoundException("Pet not found"));
        
        // When & Then
        mockMvc.perform(put("/api/pets/{id}", petId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testPetDto)))
                .andExpect(status().isNotFound());
    }
    
    @Test
    void deletePet_WithExistingId_ShouldReturnNoContent() throws Exception {
        // Given
        Long petId = 1L;
        // petService.deletePet() doesn't return anything for successful deletion
        
        // When & Then
        mockMvc.perform(delete("/api/pets/{id}", petId))
                .andExpect(status().isNoContent());
    }
    
    @Test
    void deletePet_WithNonExistingId_ShouldReturnNotFound() throws Exception {
        // Given
        Long petId = 999L;
        doThrow(new PetService.PetNotFoundException("Pet not found"))
                .when(petService).deletePet(petId);
        
        // When & Then
        mockMvc.perform(delete("/api/pets/{id}", petId))
                .andExpect(status().isNotFound());
    }
    
    @Test
    void searchPetsByName_WithValidName_ShouldReturnMatchingPets() throws Exception {
        // Given
        String searchName = "Bud";
        List<Pet> pets = Arrays.asList(testPet);
        List<PetDto> petDtos = Arrays.asList(testPetDto);
        
        when(petService.searchPetsByName(searchName)).thenReturn(pets);
        when(petMapper.toDtoList(pets)).thenReturn(petDtos);
        
        // When & Then
        mockMvc.perform(get("/api/pets/search").param("name", searchName))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("Buddy"));
    }
    
    @Test
    void getPetsByOwner_WithExistingOwner_ShouldReturnPets() throws Exception {
        // Given
        Long ownerId = 1L;
        List<Pet> pets = Arrays.asList(testPet);
        List<PetDto> petDtos = Arrays.asList(testPetDto);
        
        when(petService.findPetsByOwnerId(ownerId)).thenReturn(pets);
        when(petMapper.toDtoList(pets)).thenReturn(petDtos);
        
        // When & Then
        mockMvc.perform(get("/api/pets/owner/{ownerId}", ownerId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("Buddy"));
    }
    
    @Test
    void getPetsByOwner_WithNonExistingOwner_ShouldReturnNotFound() throws Exception {
        // Given
        Long ownerId = 999L;
        when(petService.findPetsByOwnerId(ownerId))
                .thenThrow(new PetService.OwnerNotFoundException("Owner not found"));
        
        // When & Then
        mockMvc.perform(get("/api/pets/owner/{ownerId}", ownerId))
                .andExpect(status().isNotFound());
    }
}