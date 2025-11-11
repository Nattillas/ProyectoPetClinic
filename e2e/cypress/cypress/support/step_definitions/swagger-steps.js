const { Given, When, Then } = require('@badeball/cypress-cucumber-preprocessor');
const SwaggerPage = require('../pages/SwaggerPage');

// Initialize page objects
let swaggerPage;

// Background steps
Given('the PetClinic API is running', () => {
  cy.checkApiHealth();
});

Given('I visit the Swagger UI page', () => {
  swaggerPage = new SwaggerPage();
  swaggerPage.visitSwaggerUI();
});

// Common steps
Given('I am on the Swagger UI documentation page', () => {
  cy.url().should('include', 'swagger-ui');
  swaggerPage.verifyPageIsLoaded();
});

When('I expand the {string} section', (sectionName) => {
  swaggerPage.expandSection(sectionName);
});

When('I click on the GET {string} endpoint', (endpoint) => {
  swaggerPage.clickEndpoint(endpoint);
});

// UI verification steps
Then('I should see the API documentation title', () => {
  swaggerPage.verifyApiTitle();
});

Then('I should see the available API endpoints', () => {
  swaggerPage.verifyEndpointsVisible();
});

Then('the page should be fully loaded', () => {
  swaggerPage.verifyPageIsLoaded();
});