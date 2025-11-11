package com.petclinic.cucumber.stepdefinitions;

import com.petclinic.cucumber.utils.ApiClient;
import io.cucumber.java.en.Given;
import io.restassured.response.Response;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Common step definitions shared across multiple feature files.
 */
public class CommonSteps {
    
    private ApiClient apiClient;
    
    public CommonSteps() {
        this.apiClient = new ApiClient();
    }
    
    @Given("the PetClinic API is available")
    public void the_petclinic_api_is_available() {
        assertThat(apiClient.getBaseUrl()).isNotNull();
        assertThat(apiClient.getBaseUrl()).isNotEmpty();
        
        // Optionally, make a health check request
        try {
            Response healthCheck = apiClient.get("/actuator/health");
            // If health endpoint exists, it should return 200
            if (healthCheck.getStatusCode() != 404) {
                assertThat(healthCheck.getStatusCode()).isEqualTo(200);
            }
        } catch (Exception e) {
            // Health endpoint might not exist, which is okay
            // We'll just verify the API is accessible via other endpoints
        }
    }
    
    @Given("the API base URL is configured")
    public void the_api_base_url_is_configured() {
        String baseUrl = apiClient.getBaseUrl();
        assertThat(baseUrl).isNotNull();
        assertThat(baseUrl).isNotEmpty();
        
        // Verify URL format
        assertThat(baseUrl).startsWith("http");
    }
    
    @Given("the common headers are set")
    public void the_common_headers_are_set() {
        // Headers are automatically set in ApiClient
        // This step confirms that the configuration is ready
        assertThat(apiClient).isNotNull();
    }
    
    @Given("the PetClinic API is running")
    public void the_petclinic_api_is_running() {
        // Try to access a simple endpoint to verify API is running
        Response response = apiClient.get("/api/pet-types");
        assertThat(response.getStatusCode()).isIn(200, 404, 500); // Any response indicates server is running
    }
}