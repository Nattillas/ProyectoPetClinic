#!/bin/bash

# Script to run Cypress tests for PetClinic API
# Usage: ./run-cypress-tests.sh [mode]
# mode: open (default) | run | headless

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" &> /dev/null && pwd)"
cd "$SCRIPT_DIR"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${GREEN}🚀 PetClinic Cypress E2E Test Runner${NC}"
echo -e "${GREEN}======================================${NC}"

# Check if Node.js is installed
if ! command -v node &> /dev/null; then
    echo -e "${RED}❌ Node.js is not installed. Please install Node.js version 16 or higher.${NC}"
    exit 1
fi

# Check if npm is installed
if ! command -v npm &> /dev/null; then
    echo -e "${RED}❌ npm is not installed. Please install npm.${NC}"
    exit 1
fi

# Install dependencies if node_modules doesn't exist
if [ ! -d "node_modules" ]; then
    echo -e "${YELLOW}📦 Installing dependencies...${NC}"
    npm install
fi

# Check if PetClinic is running
echo -e "${YELLOW}🔍 Checking if PetClinic API is running...${NC}"
if curl -s -f "http://localhost:8080/petclinic/swagger-ui/index.html" > /dev/null; then
    echo -e "${GREEN}✅ PetClinic is running on http://localhost:8080${NC}"
else
    echo -e "${RED}❌ PetClinic is not running on http://localhost:8080${NC}"
    echo -e "${YELLOW}   Please start the PetClinic application before running the tests.${NC}"
    exit 1
fi

# Determine run mode
MODE="${1:-open}"

case $MODE in
    "open")
        echo -e "${GREEN}🌐 Opening Cypress Test Runner...${NC}"
        npm run cypress:open
        ;;
    "run")
        echo -e "${GREEN}🏃 Running Cypress tests...${NC}"
        npm run cypress:run
        ;;
    "headless")
        echo -e "${GREEN}🏃 Running Cypress tests in headless mode...${NC}"
        npm run cypress:run:headless
        ;;
    *)
        echo -e "${RED}❌ Invalid mode: $MODE${NC}"
        echo -e "${YELLOW}Usage: $0 [open|run|headless]${NC}"
        echo -e "${YELLOW}  open     - Opens Cypress Test Runner (default)${NC}"
        echo -e "${YELLOW}  run      - Runs tests in headless mode${NC}"
        echo -e "${YELLOW}  headless - Runs tests in headless mode${NC}"
        exit 1
        ;;
esac

echo -e "${GREEN}✨ Done!${NC}"