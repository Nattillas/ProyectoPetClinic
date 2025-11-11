package com.petclinic.infrastructure.web;

import com.petclinic.application.service.PetTypeService;
import com.petclinic.infrastructure.web.dto.PetTypeDto;
import com.petclinic.infrastructure.web.mapper.PetTypeMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing pet types.
 */
@RestController
@RequestMapping("/api/pet-types")
@Tag(name = "Pet Types", description = "Pet type management API")
public class PetTypeController {

    private final PetTypeService petTypeService;
    private final PetTypeMapper petTypeMapper;

    public PetTypeController(PetTypeService petTypeService, PetTypeMapper petTypeMapper) {
        this.petTypeService = petTypeService;
        this.petTypeMapper = petTypeMapper;
    }

    /**
     * Get all pet types.
     *
     * @return List of all pet types
     */
    @GetMapping
    @Operation(
        summary = "Get all pet types",
        description = "Retrieve a list of all available pet types"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Successfully retrieved pet types"
    )
    public ResponseEntity<List<PetTypeDto>> getAllPetTypes() {
        var petTypes = petTypeService.findAll();
        var petTypeDtos = petTypes.stream()
                .map(petTypeMapper::toDto)
                .toList();
        return ResponseEntity.ok(petTypeDtos);
    }
}