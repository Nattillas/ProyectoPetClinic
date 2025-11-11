package com.petclinic.application.controller;

import com.petclinic.application.dto.PetDto;
import com.petclinic.application.mapper.PetMapper;
import com.petclinic.domain.model.Pet;
import com.petclinic.domain.service.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for Pet operations
 * This is the adapter layer that handles HTTP requests
 */
@RestController
@RequestMapping("/api/pets")
@Tag(name = "Pets", description = "Pet management API")
public class PetController {
    
    private final PetService petService;
    private final PetMapper petMapper;
    
    public PetController(PetService petService, PetMapper petMapper) {
        this.petService = petService;
        this.petMapper = petMapper;
    }
    
    @Operation(summary = "Get all pets", description = "Retrieve a list of all pets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved pets",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PetDto.class)))
    })
    @GetMapping
    public ResponseEntity<List<PetDto>> getAllPets() {
        List<Pet> pets = petService.findAllPets();
        List<PetDto> petDtos = petMapper.toDtoList(pets);
        return ResponseEntity.ok(petDtos);
    }
    
    @Operation(summary = "Get pet by ID", description = "Retrieve a pet by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pet found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PetDto.class))),
            @ApiResponse(responseCode = "404", description = "Pet not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PetDto> getPetById(
            @Parameter(description = "Pet ID", required = true)
            @PathVariable Long id) {
        Optional<Pet> pet = petService.findPetById(id);
        return pet.map(p -> ResponseEntity.ok(petMapper.toDto(p)))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @Operation(summary = "Create a new pet", description = "Create a new pet in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pet created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PetDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid pet data")
    })
    @PostMapping
    public ResponseEntity<PetDto> createPet(
            @Parameter(description = "Pet data", required = true)
            @Valid @RequestBody PetDto petDto) {
        try {
            Pet pet = petMapper.toDomain(petDto);
            Pet savedPet = petService.createPet(pet);
            PetDto savedPetDto = petMapper.toDto(savedPet);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPetDto);
        } catch (PetService.InvalidPetException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @Operation(summary = "Update a pet", description = "Update an existing pet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pet updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PetDto.class))),
            @ApiResponse(responseCode = "404", description = "Pet not found"),
            @ApiResponse(responseCode = "400", description = "Invalid pet data")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PetDto> updatePet(
            @Parameter(description = "Pet ID", required = true)
            @PathVariable Long id,
            @Parameter(description = "Updated pet data", required = true)
            @Valid @RequestBody PetDto petDto) {
        try {
            Pet petUpdates = petMapper.toDomain(petDto);
            Pet updatedPet = petService.updatePet(id, petUpdates);
            PetDto updatedPetDto = petMapper.toDto(updatedPet);
            return ResponseEntity.ok(updatedPetDto);
        } catch (PetService.PetNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (PetService.InvalidPetException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @Operation(summary = "Delete a pet", description = "Delete a pet from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pet deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Pet not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(
            @Parameter(description = "Pet ID", required = true)
            @PathVariable Long id) {
        try {
            petService.deletePet(id);
            return ResponseEntity.noContent().build();
        } catch (PetService.PetNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @Operation(summary = "Search pets by name", description = "Search pets by name pattern")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search completed",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PetDto.class)))
    })
    @GetMapping("/search")
    public ResponseEntity<List<PetDto>> searchPetsByName(
            @Parameter(description = "Pet name to search for")
            @RequestParam(required = false) String name) {
        List<Pet> pets = petService.searchPetsByName(name);
        List<PetDto> petDtos = petMapper.toDtoList(pets);
        return ResponseEntity.ok(petDtos);
    }
    
    @Operation(summary = "Get pets by owner", description = "Retrieve all pets for a specific owner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved pets",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PetDto.class))),
            @ApiResponse(responseCode = "404", description = "Owner not found")
    })
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<PetDto>> getPetsByOwner(
            @Parameter(description = "Owner ID", required = true)
            @PathVariable Long ownerId) {
        try {
            List<Pet> pets = petService.findPetsByOwnerId(ownerId);
            List<PetDto> petDtos = petMapper.toDtoList(pets);
            return ResponseEntity.ok(petDtos);
        } catch (PetService.OwnerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}