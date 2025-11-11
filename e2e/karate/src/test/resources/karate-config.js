function() {
  // Base configuration for all Karate tests
  var config = {
    // Base URL for the PetClinic API
    baseUrl: 'http://localhost:8080/petclinic',
    
    // Common headers
    headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json'
    },
    
    // Test data
    testData: {
      petTypes: ['cat', 'dog', 'bird', 'hamster', 'snake', 'lizard'],
      sampleOwner: {
        firstName: 'John',
        lastName: 'Doe',
        address: '123 Test Street',
        city: 'Test City',
        telephone: '555-1234'
      },
      samplePet: {
        name: 'Buddy',
        birthDate: '2023-01-15'
      }
    }
  };
  
  // Environment specific configuration
  var env = karate.env; // get system property 'karate.env'
  karate.log('karate.env system property was:', env);
  
  if (!env) {
    env = 'local';
  }
  
  if (env == 'local') {
    // Local development configuration
    config.baseUrl = 'http://localhost:8080/petclinic';
  } else if (env == 'test') {
    // Test environment configuration
    config.baseUrl = 'http://localhost:8080/petclinic';
  } else if (env == 'staging') {
    // Staging environment configuration
    config.baseUrl = 'https://staging-api.petclinic.com';
  }
  
  karate.log('Base URL:', config.baseUrl);
  
  return config;
}