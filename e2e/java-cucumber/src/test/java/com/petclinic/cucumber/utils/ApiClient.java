package com.petclinic.cucumber.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

/**
 * API Client utility class for PetClinic API interactions.
 * Provides a centralized way to handle REST API calls using RestAssured.
 */
public class ApiClient {
    
    private static final String DEFAULT_BASE_URL = "http://localhost:8080/petclinic";
    private String baseUrl;
    
    public ApiClient() {
        this.baseUrl = System.getProperty("api.base.url", DEFAULT_BASE_URL);
        configureRestAssured();
    }
    
    public ApiClient(String baseUrl) {
        this.baseUrl = baseUrl;
        configureRestAssured();
    }
    
    private void configureRestAssured() {
        RestAssured.baseURI = baseUrl;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        
        // Configure ObjectMapper to handle Java 8 time types
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.findAndRegisterModules();
        
        RestAssured.config = RestAssuredConfig.config().objectMapperConfig(
                ObjectMapperConfig.objectMapperConfig().jackson2ObjectMapperFactory(
                        (cls, charset) -> objectMapper
                )
        );
    }
    
    /**
     * Get the base request specification with common headers
     */
    public RequestSpecification getBaseRequestSpec() {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .log().ifValidationFails();
    }
    
    /**
     * Perform a GET request
     */
    public Response get(String endpoint) {
        return getBaseRequestSpec()
                .when()
                .get(endpoint)
                .then()
                .log().ifValidationFails()
                .extract().response();
    }
    
    /**
     * Perform a GET request with path parameters
     */
    public Response get(String endpoint, Map<String, Object> pathParams) {
        return getBaseRequestSpec()
                .pathParams(pathParams)
                .when()
                .get(endpoint)
                .then()
                .log().ifValidationFails()
                .extract().response();
    }
    
    /**
     * Perform a GET request with query parameters
     */
    public Response getWithQueryParams(String endpoint, Map<String, Object> queryParams) {
        return getBaseRequestSpec()
                .queryParams(queryParams)
                .when()
                .get(endpoint)
                .then()
                .log().ifValidationFails()
                .extract().response();
    }
    
    /**
     * Perform a POST request with body
     */
    public Response post(String endpoint, Object body) {
        return getBaseRequestSpec()
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .log().ifValidationFails()
                .extract().response();
    }
    
    /**
     * Perform a PUT request with body
     */
    public Response put(String endpoint, Object body) {
        return getBaseRequestSpec()
                .body(body)
                .when()
                .put(endpoint)
                .then()
                .log().ifValidationFails()
                .extract().response();
    }
    
    /**
     * Perform a PUT request with body and path parameters
     */
    public Response put(String endpoint, Object body, Map<String, Object> pathParams) {
        return getBaseRequestSpec()
                .body(body)
                .pathParams(pathParams)
                .when()
                .put(endpoint)
                .then()
                .log().ifValidationFails()
                .extract().response();
    }
    
    /**
     * Perform a DELETE request
     */
    public Response delete(String endpoint) {
        return getBaseRequestSpec()
                .when()
                .delete(endpoint)
                .then()
                .log().ifValidationFails()
                .extract().response();
    }
    
    /**
     * Perform a DELETE request with path parameters
     */
    public Response delete(String endpoint, Map<String, Object> pathParams) {
        return getBaseRequestSpec()
                .pathParams(pathParams)
                .when()
                .delete(endpoint)
                .then()
                .log().ifValidationFails()
                .extract().response();
    }
    
    /**
     * Get the current base URL
     */
    public String getBaseUrl() {
        return baseUrl;
    }
    
    /**
     * Set a new base URL
     */
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        RestAssured.baseURI = baseUrl;
    }
}