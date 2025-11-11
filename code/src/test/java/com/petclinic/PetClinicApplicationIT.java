package com.petclinic;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration test for PetClinic application
 * Tests that the application context loads correctly and endpoints are accessible
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.sql.init.mode=never"
})
class PetClinicApplicationIT {
    
    @LocalServerPort
    private int port;
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    void contextLoads() {
        // This test will pass if the application context loads successfully
        // It validates the hexagonal architecture wiring and bean configuration
    }
    
    @Test
    void shouldReturnPetTypes() {
        // Given: Application is running with sample data
        String url = "http://localhost:" + port + "/petclinic/api/pet-types";
        
        // When: We call the pet types endpoint
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        
        // Then: We should get a successful response with pet types
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).contains("cat");
        assertThat(response.getBody()).contains("dog");
        assertThat(response.getBody()).contains("bird");
    }
    
    @Test
    void shouldReturnAllPets() {
        // Given: Application is running with sample data
        String url = "http://localhost:" + port + "/petclinic/api/pets";
        
        // When: We call the pets endpoint
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        
        // Then: We should get a successful response with pets data
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).contains("Leo");
        assertThat(response.getBody()).contains("Basil");
        assertThat(response.getBody()).contains("Rosy");
    }
    
    @Test
    void shouldReturnSwaggerApiDocs() {
        // Given: Application is running with OpenAPI documentation
        String url = "http://localhost:" + port + "/petclinic/api-docs";
        
        // When: We call the API docs endpoint
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        
        // Then: We should get OpenAPI specification
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).contains("openapi");
        assertThat(response.getBody()).contains("PetClinic API");
    }
}