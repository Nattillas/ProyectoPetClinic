Feature: Swagger UI API Testing
  As a developer
  I want to test the PetClinic API through Swagger UI
  So that I can verify the REST endpoints work correctly from the documentation interface

  Background:
    Given the PetClinic API is running
    And I visit the Swagger UI page

  Scenario: Verify Swagger UI loads correctly
    Given I am on the Swagger UI documentation page
    Then I should see the API documentation title
    And I should see the available API endpoints
    And the page should be fully loaded

  Scenario: Test GET /api/pets endpoint through Swagger UI
    Given I am on the Swagger UI documentation page
    When I expand the "Pets" section
    And I click on the GET "/api/pets" endpoint
    And I click the "Try it out" endpoint
    And I click the "Execute" button
    Then I should see a response with status code 200

    # TODO: Implementar los pasos restantes para completar la prueba del endpoint
    # Sugerencia: Agregar pasos para "Execute" y validación de respuesta