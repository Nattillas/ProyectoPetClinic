// cypress/support/pages/components/ResponseSection.js
// Component for handling API response validation in Swagger UI

class ResponseSection {
  constructor() {
    this.selectors = {
      // Response containers
      responseWrapper: '.responses-wrapper, .live-responses-table, table',
      responseTable: 'table',
      responseRow: 'table tr',
      
      // Response content
      responseBody: 'h5:contains("Response body")',
      responseCode: 'code',
      statusCode: 'table tr',
      
      // Response headers
      responseHeaders: 'h5:contains("Response headers")'
    };
    
    this.timeout = 15000;
  }

  verifyResponseIsVisible() {
    cy.get(this.selectors.responseWrapper, { timeout: this.timeout }).should('be.visible');
    return this;
  }

  verifyStatusCode(expectedStatus) {
    cy.get(this.selectors.statusCode).should('contain.text', expectedStatus);
    return this;
  }

  verifySuccessfulResponse() {
    this.verifyStatusCode('200');
    return this;
  }

  verifyResponseBodyExists() {
    cy.get(this.selectors.responseBody).should('be.visible');
    return this;
  }

  verifyResponseContainsData() {
    this.verifyResponseBodyExists();
    cy.get(this.selectors.responseCode).should('be.visible').and('contain.text', '{');
    return this;
  }

  verifyPetsData() {
    this.verifyResponseContainsData();
    // Additional pet-specific validations could be added here
    return this;
  }

  verifyPetTypesData() {
    this.verifyResponseContainsData();
    cy.get(this.selectors.responseCode).should('contain.text', 'name');
    return this;
  }

  // Method to get response data for further processing
  getResponseData() {
    return cy.get(this.selectors.responseCode);
  }
}

module.exports = ResponseSection;