package com.petclinic.cucumber.stepdefinitions;

import com.petclinic.cucumber.models.PetType;
import com.petclinic.cucumber.utils.ApiClient;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for Pet Types API tests.
 */
public class PetTypeSteps {
    
    private ApiClient apiClient;
    private Response response;
    private List<PetType> petTypes;
    
    public PetTypeSteps() {
        this.apiClient = new ApiClient();
    }
    
    // ADD PET TYPES STEPS HERE
}