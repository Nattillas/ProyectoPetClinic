#!/bin/bash

# Script para ejecutar tests Karate E2E
# Usage: ./run-karate-tests.sh [tag]

set -e

echo "🚀 PetClinic Karate E2E Tests"
echo "==============================="

# Check if API is running
echo "📡 Checking if PetClinic API is running..."
if curl -s -f http://localhost:8080/petclinic/api/pet-types > /dev/null; then
    echo "✅ API is running at http://localhost:8080/petclinic"
else
    echo "❌ API is not running!"
    echo "Please start the API first:"
    echo "  cd ../../"
    echo "  mvn spring-boot:run"
    exit 1
fi

# Change to Karate directory
cd "$(dirname "$0")"

echo ""
echo "🧪 Running Karate tests..."

# Check if tag parameter is provided
if [ $# -eq 0 ]; then
    echo "Running all tests..."
    mvn test
elif [ "$1" = "smoke" ]; then
    echo "Running smoke tests..."
    mvn test -Dkarate.options="--tags @smoke"
elif [ "$1" = "crud" ]; then
    echo "Running CRUD workflow tests..."
    mvn test -Dkarate.options="--tags @crud"
elif [ "$1" = "pet-types" ]; then
    echo "Running pet-types tests..."
    mvn test -Dkarate.options="--tags @pet-types"
else
    echo "Running tests with tag: $1"
    mvn test -Dkarate.options="--tags @$1"
fi

echo ""
echo "📊 Test execution completed!"
echo "Check reports in: target/karate-reports/karate-summary.html"