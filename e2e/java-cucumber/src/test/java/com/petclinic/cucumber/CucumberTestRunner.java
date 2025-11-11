package com.petclinic.cucumber;

import io.cucumber.junit.platform.engine.Constants;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

/**
 * Main test runner for Cucumber E2E tests.
 * This class configures and executes all Cucumber feature files.
 * 
 * Usage:
 * - Run all tests: mvn test
 * - Run specific tags: mvn test -Dcucumber.filter.tags="@smoke"
 * - Run in parallel: mvn test -P parallel
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME, 
    value = "pretty, html:target/cucumber-reports/cucumber.html, json:target/cucumber-reports/cucumber.json, junit:target/cucumber-reports/cucumber.xml")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, 
    value = "com.petclinic.cucumber.stepdefinitions")
@ConfigurationParameter(key = Constants.FILTER_TAGS_PROPERTY_NAME, 
    value = "not @ignore")
@ConfigurationParameter(key = Constants.PLUGIN_PUBLISH_ENABLED_PROPERTY_NAME, 
    value = "false")
@ConfigurationParameter(key = Constants.PLUGIN_PUBLISH_QUIET_PROPERTY_NAME, 
    value = "true")
public class CucumberTestRunner {
    // This class serves as the test suite runner
    // Configuration is handled via annotations above
}