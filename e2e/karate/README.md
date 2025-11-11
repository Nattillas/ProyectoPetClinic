# PetClinic API - Karate E2E Tests

Este directorio contiene las pruebas End-to-End (E2E) para la API de PetClinic utilizando el framework **Karate**.

## 📋 Estructura

```
e2e/karate/
├── pom.xml                           # Configuración Maven para Karate
├── src/test/java/
│   └── com/petclinic/karate/
│       └── KarateTestRunner.java     # Ejecutor principal de tests
└── src/test/resources/
    ├── karate-config.js              # Configuración global de Karate
    └── petclinic/
        └── pet-types.feature         # Tests específicos de tipos de mascota
```

## 🧪 Escenarios de Prueba

### Tests de Tipos de Mascota (`pet-types.feature`)

- ✅ Verificar endpoint de tipos de mascota
- ✅ Validar estructura de datos
- ✅ Verificar integridad de datos
- ✅ Comprobar tipos esperados (cat, dog, bird, etc.)

## 🚀 Ejecución de Tests

### Prerrequisitos

1. **Asegurar que la API está ejecutándose**:
   ```bash
   cd ../..  # Volver al proyecto principal
   mvn spring-boot:run
   ```
   
   La API debe estar disponible en: `http://localhost:8080/petclinic`

### Ejecutar Tests Karate

2. **Desde el directorio de Karate**:
   ```bash
   cd e2e/karate
   mvn test
   ```

3. **Ejecutar tests específicos por etiqueta**:
   ```bash
   # Solo tests de smoke
   mvn test -Dkarate.options="--tags @smoke"
   
   # Solo flujo CRUD completo
   mvn test -Dkarate.options="--tags @crud"
   
   # Solo tests de pet-types
   mvn test -Dkarate.options="--tags @pet-types"
   ```

4. **Ejecutar con entorno específico**:
   ```bash
   mvn test -Dkarate.env=test
   mvn test -Dkarate.env=local
   ```

## 📊 Reportes

Después de ejecutar los tests, Karate genera reportes en:
- `target/karate-reports/karate-summary.html` - Reporte principal
- `target/surefire-reports/` - Reportes JUnit

## ⚙️ Configuración

### karate-config.js
- Define la URL base de la API
- Configura headers comunes
- Maneja configuraciones por entorno
- Define datos de prueba reutilizables

### Variables de Entorno
- `karate.env` - Define el entorno (local, test, staging)
- `karate.options` - Opciones adicionales para ejecución

## 🏷️ Tags de Tests

- `@smoke` - Tests básicos de conectividad
- `@crud` - Tests de operaciones CRUD completas
- `@pet-workflow` - Flujo completo de gestión de mascotas
- `@api-docs` - Verificación de documentación API
- `@validation` - Tests de validación de datos
- `@search` - Tests de funcionalidad de búsqueda
- `@pet-types` - Tests específicos de tipos de mascota
- `@data-validation` - Validación de integridad de datos

## 🔧 Personalización

Para añadir nuevos tests:

1. Crear nuevos archivos `.feature` en `src/test/resources/petclinic/`
2. Usar la sintaxis Gherkin con keywords de Karate
3. Aprovechar la configuración global en `karate-config.js`
4. Añadir tags apropiados para categorización

## 📖 Documentación

- [Karate Framework](https://github.com/intuit/karate)
- [Karate API Testing](https://github.com/intuit/karate#api-testing)
- [Gherkin Syntax](https://cucumber.io/docs/gherkin/)