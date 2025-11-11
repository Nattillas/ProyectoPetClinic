# PetClinic API - Java Cucumber E2E Tests

Este directorio contiene las pruebas End-to-End (E2E) para la API de PetClinic utilizando **Java Cucumber** con **RestAssured**.

## 📋 Estructura del Proyecto

```
e2e/java-cucumber/
├── pom.xml                                     # Configuración Maven con dependencias
├── .tool-versions                              # Versiones de herramientas (Java, Maven)
├── run-cucumber-tests.sh                      # Script de ejecución de tests
├── src/test/
│   ├── java/com/petclinic/cucumber/
│   │   ├── CucumberTestRunner.java             # Ejecutor principal de tests
│   │   ├── models/                             # Modelos de datos
│   │   │   ├── Pet.java                        # Modelo Pet
│   │   │   ├── PetType.java                    # Modelo PetType
│   │   │   └── Owner.java                      # Modelo Owner
│   │   ├── stepdefinitions/                    # Definiciones de pasos
│   │   │   ├── CommonSteps.java                # Pasos comunes
│   │   │   ├── PetTypeSteps.java               # Pasos para Pet Types
│   │   │   └── PetClinicApiSteps.java          # Pasos para API completa
│   │   └── utils/                              # Utilidades
│   │       └── ApiClient.java                  # Cliente API con RestAssured
│   └── resources/
│       ├── features/                           # Archivos Feature de Cucumber
│       │   ├── pet-types.feature               # Tests de tipos de mascota
│       └── application-test.properties         # Configuración de pruebas
```

## 🧪 Escenarios de Prueba

### 1. Pet Types API Tests (`pet-types.feature`)

**Funcionalidades Cubiertas**:
- ✅ **Validación de Endpoint**: Verificar que el endpoint `/api/pet-types` responde correctamente
- ✅ **Estructura de Datos**: Validar que cada pet type tiene `id` (number) y `name` (string)
- ✅ **Tipos Esperados**: Verificar presencia de tipos básicos (cat, dog, bird)
- ✅ **Integridad de Datos**: Validar que no hay valores nulos o vacíos
- ✅ **IDs Únicos**: Verificar que cada pet type tiene un ID único
- ✅ **Esquema JSON**: Validar estructura y formato de la respuesta

**Tags Disponibles**: `@smoke`, `@pet-types`, `@api`, `@validation`, `@data-integrity`, `@schema`

## 🚀 Ejecución de Tests

### Prerrequisitos

1. **Java 17** o superior
2. **Maven 3.6** o superior
3. **PetClinic API ejecutándose** en `http://localhost:8080/petclinic`

### Iniciar PetClinic API

```bash
# Desde el directorio raíz del proyecto
cd code/
mvn spring-boot:run
```

La API debe estar disponible en: `http://localhost:8080/petclinic`

### Opciones de Ejecución

#### 1. Ejecutar Todos los Tests

```bash
cd e2e/java-cucumber
mvn test
```

#### 2. (Alernativa) Usar el Script de Ejecución

```bash
# Ejecutar todos los tests
./run-cucumber-tests.sh

# Tests con tags específicos
./run-cucumber-tests.sh -t @smoke
./run-cucumber-tests.sh -t @pet-types
./run-cucumber-tests.sh -t "@crud and @pet-workflow"

# Tests en entorno específico
./run-cucumber-tests.sh -e test

# Limpiar y ejecutar con reportes
./run-cucumber-tests.sh -c -r

# Ver todas las opciones
./run-cucumber-tests.sh -h
```

#### 3. (Recomendado) Ejecución por Tags con Maven

```bash
# Solo tests de smoke
mvn test -Dcucumber.filter.tags="@smoke"

# Solo tests de pet-types
mvn test -Dcucumber.filter.tags="@pet-types"

# Combinación de tags
mvn test -Dcucumber.filter.tags="@crud and @pet-workflow"

# Excluir tests específicos
mvn test -Dcucumber.filter.tags="not @ignore"
```


#### 4. Perfiles de Entorno

```bash
# Entorno de desarrollo (default)
mvn test -P dev

# Entorno de test
mvn test -P test
```

## 📊 Reportes y Resultados

Después de ejecutar los tests, se generan varios tipos de reportes:

### Reportes Cucumber
- **HTML Report**: `target/cucumber-reports/index.html`
- **JSON Report**: `target/cucumber-reports/Cucumber.json`
- **XML Report**: `target/cucumber-reports/Cucumber.xml`

### Reportes Maven
- **Surefire Reports**: `target/surefire-reports/`

### Visualizar Reportes

```bash
# Abrir reporte HTML principal
xdg-open target/cucumber-reports/index.html

# O usando el script con -r
./run-cucumber-tests.sh -r
```

## ⚙️ Configuración

### Configuración de Entorno

La configuración se maneja a través de:

1. **application-test.properties**: Configuraciones base
2. **Perfiles Maven**: Configuraciones específicas por entorno
3. **Variables del Sistema**: Override de configuraciones

### Variables de Configuración Clave

```properties
# URL base de la API
api.base.url=http://localhost:8080/petclinic

# Configuración de timeouts
api.timeout=30
api.retry.attempts=3

# Entorno de ejecución
test.environment=local
```

### Override de Configuración

```bash
# Cambiar URL base
mvn test -Dapi.base.url=http://test-server:8080/petclinic

# Cambiar entorno
mvn test -Dtest.environment=staging

# Configurar tags
mvn test -Dcucumber.filter.tags="@smoke"
```

## 🏷️ Sistema de Tags

### Tags por Funcionalidad
- `@smoke` - Tests básicos de conectividad
- `@health-check` - Verificación de salud de la API
- `@pet-types` - Tests específicos de tipos de mascota
- `@crud` - Operaciones CRUD completas
- `@pet-workflow` - Flujo completo de gestión de mascotas

### Tags por Tipo de Test
- `@api` - Tests de API
- `@validation` - Tests de validación de datos
- `@error-handling` - Tests de manejo de errores
- `@schema` - Tests de validación de esquemas
- `@data-integrity` - Tests de integridad de datos

### Tags por Alcance
- `@end-to-end` - Tests de extremo a extremo
- `@search` - Tests de funcionalidad de búsqueda
- `@api-docs` - Tests de documentación de API

### Usar Tags

```bash
# Ejecutar solo smoke tests
mvn test -Dcucumber.filter.tags="@smoke"

# Ejecutar tests de pet-types y validation
mvn test -Dcucumber.filter.tags="@pet-types and @validation"

# Ejecutar todos excepto los ignorados
mvn test -Dcucumber.filter.tags="not @ignore"

# Ejecutar smoke O crud tests
mvn test -Dcucumber.filter.tags="@smoke or @crud"
```

## 🔧 Desarrollo y Personalización

### Añadir Nuevos Tests

1. **Crear/Modificar Feature Files** en `src/test/resources/features/`
2. **Implementar Step Definitions** en `src/test/java/.../stepdefinitions/`
3. **Añadir Modelos** si es necesario en `src/test/java/.../models/`
4. **Usar Tags apropiados** para categorización

### Estructura de Step Definitions

```java
@Given("I have a condition")
public void i_have_a_condition() {
    // Setup code
}

@When("I perform an action")
public void i_perform_an_action() {
    // Action code using ApiClient
}

@Then("I should see a result")
public void i_should_see_a_result() {
    // Assertion code using AssertJ
}
```

### Usar ApiClient

```java
// GET request
Response response = apiClient.get("/api/pet-types");

// POST request
Response response = apiClient.post("/api/pets", petObject);

// GET with parameters
Response response = apiClient.getWithQueryParams("/api/pets", 
    Map.of("name", "Buddy"));
```

## 🔍 Solución de Problemas

### Problemas Comunes

1. **API no accesible**
   ```bash
   # Verificar que PetClinic está ejecutándose
   curl http://localhost:8080/petclinic/api/pet-types
   ```

2. **Tests fallan por timeouts**
   ```bash
   # Aumentar timeout
   mvn test -Dapi.timeout=60
   ```

3. **Dependencias Maven**
   ```bash
   # Limpiar y reinstalar dependencias
   mvn clean install
   ```

4. **Problemas de compilación**
   ```bash
   # Verificar versión de Java
   java --version
   mvn --version
   ```

### Debugging

1. **Logs detallados**:
   ```bash
   mvn test -Dlogging.level.com.petclinic.cucumber=DEBUG
   ```

2. **Ver requests/responses de RestAssured**:
   ```bash
   mvn test -Dlogging.level.io.restassured=DEBUG
   ```

3. **Ejecutar un solo test**:
   ```bash
   mvn test -Dcucumber.filter.tags="@smoke"
   ```

## 📖 Referencias

- [Cucumber Documentation](https://cucumber.io/docs/)
- [RestAssured Documentation](https://rest-assured.io/)
- [AssertJ Documentation](https://assertj.github.io/doc/)
- [JUnit 5 Documentation](https://junit.org/junit5/docs/current/user-guide/)
- [Maven Documentation](https://maven.apache.org/guides/)