// cypress/support/commands.js
// Essential commands for the hiring test

// Command to check if the PetClinic API is running
Cypress.Commands.add('checkApiHealth', () => {
  cy.request({
    method: 'GET',
    url: `${Cypress.env('petclinicApiUrl')}/api/pet-types`,
    failOnStatusCode: false
  }).then((response) => {
    expect(response.status).to.be.oneOf([200, 404]);
  });
});