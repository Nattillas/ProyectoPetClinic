// cypress/support/e2e.js
// This file is processed and loaded automatically before your test files.

require('./commands');

// Disable uncaught exception handling for better test stability
Cypress.on('uncaught:exception', (err, runnable) => {
  // returning false here prevents Cypress from failing the test
  console.warn('Uncaught exception:', err.message);
  return false;
});