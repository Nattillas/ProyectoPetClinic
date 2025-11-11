// cypress/support/pages/SwaggerPage.js
// Page Object for Swagger UI main page

const BasePage = require('./BasePage');
const SwaggerEndpoint = require('./components/SwaggerEndpoint');
const ResponseSection = require('./components/ResponseSection');

class SwaggerPage extends BasePage {
  constructor() {
    super();
    
    // Page selectors
    this.selectors = {
      container: '.swagger-ui',
      infoSection: '.info',
      title: '.info .title, h1, .info',
      endpoints: '.opblock, .operations-tag',
      endpointSummary: '.opblock-summary-path, .opblock-summary',
      sectionHeaders: 'h3',
      // Specific sections
      petsSection: 'h3:contains("Pets")',
      petTypesSection: 'h3:contains("Pet Types")'
    };

    // Initialize components
    this.endpoint = new SwaggerEndpoint();
    this.responseSection = new ResponseSection();
  }

  visitSwaggerUI() {
    this.visit(`${Cypress.env('petclinicApiUrl')}/swagger-ui/index.html`);
    return this.waitForPageLoad();
  }

  waitForPageLoad() {
    this.waitForElement(this.selectors.container, this.timeout.extended);
    this.waitForElement(this.selectors.infoSection, this.timeout.default);
    cy.wait(2000); // Allow for dynamic content to load
    return this;
  }

  verifyPageIsLoaded() {
    this.verifyElementVisible(this.selectors.container);
    this.verifyElementVisible(this.selectors.infoSection);
    return this;
  }

  verifyApiTitle() {
    this.verifyElementVisible(this.selectors.title);
    this.verifyPageContainsText('PetClinic');
    return this;
  }

  verifyEndpointsVisible() {
    cy.get(this.selectors.endpoints).should('have.length.greaterThan', 0);
    this.verifyElementVisible(this.selectors.endpointSummary);
    return this;
  }

  expandSection(sectionName) {
    // Sections are already expanded in current UI, just verify visibility
    if (sectionName.toLowerCase() === 'pets') {
      cy.get('h3').contains('Pets').should('be.visible');
    } else if (sectionName.toLowerCase() === 'pet types') {
      cy.get('h3').contains('Pet Types').should('be.visible');
    } else {
      cy.get('h3, h4').contains(sectionName, { matchCase: false }).should('be.visible');
    }
    return this;
  }

  clickEndpoint(endpointPath) {
    return this.endpoint.clickEndpoint(endpointPath);
  }

  // Getter methods for components
  getEndpointComponent() {
    return this.endpoint;
  }

  getResponseComponent() {
    return this.responseSection;
  }
}

module.exports = SwaggerPage;