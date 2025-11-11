// cypress/support/pages/components/SwaggerEndpoint.js
// Component for individual Swagger endpoint interactions

class SwaggerEndpoint {
  constructor() {
    this.selectors = {
      // Endpoint links (discovered via Playwright)
      petsEndpoint: 'a[href="#/Pets/getAllPets"]',
      petTypesEndpoint: 'a[href="#/Pet%20Types/getAllPetTypes"]',
      
      // Endpoint interaction elements
      endpointBody: '.opblock-body',
      tryItOutButton: 'button:contains("Try it out")',
      executeButton: 'button:contains("Execute")',
      
      // Fallback selectors
      endpointBlock: '.opblock.opblock-get, .opblock',
      endpointPath: '.opblock-summary-path, .opblock-summary'
    };
    
    // Timeout settings
    this.timeout = 5000;
  }

  clickEndpoint(endpointPath) {
    if (endpointPath === '/api/pets') {
      cy.get(this.selectors.petsEndpoint).should('be.visible').click();
    } else if (endpointPath === '/api/pet-types') {
      cy.get(this.selectors.petTypesEndpoint).should('be.visible').click();
    } else {
      // Fallback for other endpoints
      this._clickEndpointFallback(endpointPath);
    }
    
    // Wait for endpoint details to expand
    cy.get(this.selectors.endpointBody, { timeout: this.timeout }).should('be.visible');
    return this;
  }

  clickTryItOut() {
    cy.get(this.selectors.tryItOutButton, { timeout: this.timeout })
      .should('be.visible')
      .click();
    return this;
  }

  clickExecute() {
    cy.get(this.selectors.executeButton, { timeout: this.timeout })
      .should('be.visible')
      .click();
    return this;
  }

  // Private method for fallback endpoint clicking
  _clickEndpointFallback(endpointPath) {
    cy.get(this.selectors.endpointBlock)
      .filter(':contains("GET")')
      .find(this.selectors.endpointPath)
      .contains(endpointPath)
      .should('be.visible')
      .click();
  }
}

module.exports = SwaggerEndpoint;