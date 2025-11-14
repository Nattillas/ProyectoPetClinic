Feature: Pet Types API Tests
  
  Background:
    * url baseUrl
    * configure headers = headers

  # En este escenario se va a hacer un caso de prueba en el que se crea una mascota nueva, se verifica su correcta
  # creación y luego se elimina para dejar el sistema como estaba antes de la prueba. Finalmente, se intenta obtener
  # la mascota de nuevo para ver que se ha eliminado correctamente.
  @pet-types
  Scenario: Create new pet, verify creation, and delete pet

    # Se lee el json que contiene los datos de la nueva mascota a crear
    * def payload = read('classpath:data/newPet.json')

    # Se crea la nueva mascota
    Given path '/api/pets'
    And request payload
    When method POST
    Then status 201

    # Se obtiene el ID de la mascota creada para futuras operaciones
    * def createdId = response.id
    * print 'Created Pet ID:', createdId
    * set payload.id = createdId

    # Se verifica que la mascota se ha creado correctamente
    Given path '/api/pets/' + (createdId)
    When method GET
    Then status 200
    And match response.id == createdId

    # Se elimina la mascota creada para limpiar el sistema
    Given path '/api/pets/' + (createdId)
    When method DELETE
    Then status 204

    # Se intenta obtener la mascota eliminada para verificar que ya no existe
    Given path '/api/pets/' + (createdId)
    When method GET
    Then status 404

