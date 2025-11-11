#!/bin/bash

# PetClinic Cucumber E2E Tests Execution Script
# This script provides various options for running the Cucumber tests

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Default values
ENVIRONMENT="local"
TAGS=""
PARALLEL="false"
PROFILE="dev"

# Function to print colored output
print_colored() {
    local color=$1
    local message=$2
    echo -e "${color}${message}${NC}"
}

# Function to show usage
show_usage() {
    echo "Usage: $0 [OPTIONS]"
    echo ""
    echo "Options:"
    echo "  -e, --env ENVIRONMENT    Set test environment (local, test, staging)"
    echo "  -t, --tags TAGS          Run tests with specific tags (e.g., @smoke, @pet-types)"
    echo "  -p, --parallel           Run tests in parallel"
    echo "  -f, --profile PROFILE    Maven profile to use (dev, test, parallel)"
    echo "  -c, --clean              Clean before running tests"
    echo "  -r, --reports            Open reports after execution"
    echo "  -h, --help               Show this help message"
    echo ""
    echo "Examples:"
    echo "  $0                                    # Run all tests with default settings"
    echo "  $0 -e test -t @smoke                 # Run smoke tests in test environment"
    echo "  $0 -t '@pet-types and @validation'   # Run pet-types validation tests"
    echo "  $0 -p -f parallel                    # Run tests in parallel"
    echo "  $0 -c -r                             # Clean, run tests, and open reports"
}

# Parse command line arguments
while [[ $# -gt 0 ]]; do
    case $1 in
        -e|--env)
            ENVIRONMENT="$2"
            shift 2
            ;;
        -t|--tags)
            TAGS="$2"
            shift 2
            ;;
        -p|--parallel)
            PARALLEL="true"
            PROFILE="parallel"
            shift
            ;;
        -f|--profile)
            PROFILE="$2"
            shift 2
            ;;
        -c|--clean)
            CLEAN="true"
            shift
            ;;
        -r|--reports)
            OPEN_REPORTS="true"
            shift
            ;;
        -h|--help)
            show_usage
            exit 0
            ;;
        *)
            echo "Unknown option: $1"
            show_usage
            exit 1
            ;;
    esac
done

# Print execution details
print_colored $BLUE "🚀 PetClinic Cucumber E2E Tests"
print_colored $BLUE "=================================="
echo "Environment: $ENVIRONMENT"
echo "Profile: $PROFILE"
echo "Tags: ${TAGS:-'All tests'}"
echo "Parallel: $PARALLEL"
echo ""

# Check if API is running
print_colored $YELLOW "⏳ Checking if PetClinic API is accessible..."
API_URL="http://localhost:8080/petclinic"
if curl -sf "$API_URL/api/pet-types" > /dev/null 2>&1; then
    print_colored $GREEN "✅ PetClinic API is accessible at $API_URL"
else
    print_colored $RED "❌ PetClinic API is not accessible at $API_URL"
    print_colored $YELLOW "   Please start the PetClinic application first:"
    print_colored $YELLOW "   cd ../../code && mvn spring-boot:run"
    exit 1
fi

# Clean if requested
if [[ "$CLEAN" == "true" ]]; then
    print_colored $YELLOW "🧹 Cleaning previous build artifacts..."
    mvn clean
fi

# Build Maven command
MVN_CMD="mvn test -P$PROFILE"

# Add environment properties
MVN_CMD="$MVN_CMD -Dtest.environment=$ENVIRONMENT"

# Add tags if specified
if [[ -n "$TAGS" ]]; then
    MVN_CMD="$MVN_CMD -Dcucumber.filter.tags=\"$TAGS\""
fi

# Execute tests
print_colored $YELLOW "🧪 Executing Cucumber tests..."
echo "Command: $MVN_CMD"
echo ""

if eval $MVN_CMD; then
    print_colored $GREEN "✅ Tests completed successfully!"
    
    # Show reports information
    echo ""
    print_colored $BLUE "📊 Reports generated:"
    echo "  - Cucumber HTML: target/cucumber-reports/index.html"
    echo "  - Cucumber JSON: target/cucumber-reports/Cucumber.json"
    echo "  - JUnit XML: target/cucumber-reports/Cucumber.xml"
    echo "  - Surefire Reports: target/surefire-reports/"
    
    # Open reports if requested
    if [[ "$OPEN_REPORTS" == "true" ]]; then
        print_colored $YELLOW "🌐 Opening test reports..."
        if command -v xdg-open &> /dev/null; then
            xdg-open target/cucumber-reports/index.html
        elif command -v open &> /dev/null; then
            open target/cucumber-reports/index.html
        else
            print_colored $YELLOW "Cannot automatically open reports. Please open target/cucumber-reports/index.html manually."
        fi
    fi
    
    exit 0
else
    print_colored $RED "❌ Tests failed!"
    
    echo ""
    print_colored $YELLOW "📋 Troubleshooting:"
    echo "  1. Check if PetClinic API is running and accessible"
    echo "  2. Verify test environment configuration"
    echo "  3. Review test logs for specific failures"
    echo "  4. Check target/cucumber-reports/ for detailed reports"
    
    exit 1
fi