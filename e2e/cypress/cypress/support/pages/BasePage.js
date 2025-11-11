// cypress/support/pages/BasePage.js
// Base page object with common functionality

class BasePage {
  constructor() {
    this.timeout = {
      default: 10000,
      extended: 30000
    };
  }

  visit(url) {
    cy.visit(url);
    return this;
  }

  waitForElement(selector, timeout = this.timeout.default) {
    cy.get(selector, { timeout }).should('be.visible');
    return this;
  }

  clickElement(selector) {
    cy.get(selector).should('be.visible').click();
    return this;
  }

  verifyElementExists(selector) {
    cy.get(selector).should('exist');
    return this;
  }

  verifyElementVisible(selector) {
    cy.get(selector).should('be.visible');
    return this;
  }

  verifyElementContainsText(selector, text) {
    cy.get(selector).should('contain.text', text);
    return this;
  }

  verifyPageContainsText(text) {
    cy.get('body').should('contain.text', text);
    return this;
  }
}

module.exports = BasePage;