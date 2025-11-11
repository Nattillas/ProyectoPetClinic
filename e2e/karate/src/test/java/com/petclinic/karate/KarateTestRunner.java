package com.petclinic.karate;

import com.intuit.karate.junit5.Karate;

/**
 * Main test runner for Karate E2E tests.
 * This class executes all Karate feature files in the classpath.
 */
public class KarateTestRunner {

    /**
     * Run all tests in the 'petclinic' package
     */
    @Karate.Test
    Karate testPetClinic() {
        return Karate.run("classpath:petclinic").relativeTo(getClass());
    }
}