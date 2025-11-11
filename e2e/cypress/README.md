# Cypress E2E Testing Project with Page Object Model

Este proyecto contiene pruebas end-to-end de Cypress para la aplicación Swagger UI de PetClinic, implementado utilizando el patrón **Page Object Model (POM)** con integración de **Cucumber BDD**.

## 🖼️ Referencia Visual de Swagger UI

![Swagger UI Reference](swagger-pic.png)

La imagen anterior muestra la interfaz de Swagger UI de PetClinic que utilizamos como base para nuestras pruebas automatizadas (ejemplo). Observa los elementos clave:

### 🛠️ **Cómo Crear Tests Basados en esta UI:**

1. **🔍 Identificar Secciones**: Localiza las secciones principales (Pet Types, Pets)
2. **🗺️ Mapear Endpoints**: Identifica cada endpoint y su método HTTP
3. **📝 Definir Flujo**: Planifica el flujo de interacción (expandir → click endpoint → try it out → execute → validar)
4. **🏗️ Implementar Page Objects**: Crea selectores CSS para cada elemento visual
5. **✅ Escribir Escenarios BDD**: Traduce el flujo visual a pasos Gherkin comprensibles

**💡 Ejemplo de flujo de test completo:**
1. Navegar a Swagger UI → 2. Expandir sección "Pets" → 3. Click en GET "/api/pets" → 4. Click "Try it out" → 5. Click "Execute" → 6. Validar respuesta HTTP 200

## Estructura del Proyecto con POM
```
cypress/
├── cypress.config.js                    # Configuración principal de Cypress
├── package.json                         # Dependencias y scripts npm
├── cypress/
│   ├── e2e/
│   │   └── swagger-api-test.feature     # Escenarios BDD en Gherkin
│   └── support/
│       ├── e2e.js                       # Configuración global
│       ├── commands.js                  # Comandos personalizados esenciales
│       ├── pages/                       # 📁 Page Object Model
│       │   ├── BasePage.js             # ⭐ Clase base con funcionalidad común
│       │   ├── SwaggerPage.js          # 🏠 Página principal de Swagger UI
│       │   └── components/             # 📁 Componentes reutilizables
│       │       ├── SwaggerEndpoint.js  # 🔗 Interacciones con endpoints
│       │       └── ResponseSection.js  # ✅ Validación de respuestas
│       └── step_definitions/
│           └── swagger-steps.js        # Steps que utilizan Page Objects
└── README.md                           # Documentación
```

## 🚀 Inicio Rápido

```bash
# Instalar dependencias (Node.js 20.11.0 requerido)
npm install

# Ejecutar pruebas en modo headless
npm test

# Ejecutar pruebas con la interfaz de Cypress
npm run cy:open

# Ejecutar pruebas específicas
npx cypress run --spec "cypress/e2e/swagger-api-test.feature"
```

## 📋 Patrón Page Object Model

### BasePage.js - Funcionalidad Común
```javascript
class BasePage {
  visit(url) { /* Navegación */ }
  waitForElement(selector, timeout) { /* Esperas */ }
  clickElement(selector) { /* Interacciones */ }
  verifyElementVisible(selector) { /* Verificaciones */ }
}
```

### SwaggerPage.js - Página Principal
```javascript
class SwaggerPage extends BasePage {
  constructor() {
    super();
    this.endpoint = new SwaggerEndpoint();     // Composición de componentes
    this.response = new ResponseSection();
  }
  
  visitSwaggerUI() { /* Navegación específica */ }
  expandSection(sectionName) { /* Interacciones de página */ }
  clickEndpoint(endpoint) { /* Delegación a componentes */ }
}
```

### Componentes Especializados
- **SwaggerEndpoint.js**: Maneja interacciones con endpoints específicos
- **ResponseSection.js**: Valida respuestas de API y códigos de estado

## 🧪 Ejemplos de Uso

### Uso Fluido con Page Objects
```javascript
// En los step definitions
const swaggerPage = new SwaggerPage();

Given('I visit the Swagger UI page', () => {
  swaggerPage.visitSwaggerUI();
});

When('I expand the {string} section', (sectionName) => {
  swaggerPage.expandSection(sectionName);
});

When('I click on the GET {string} endpoint', (endpoint) => {
  swaggerPage.clickEndpoint(endpoint);
});
```

### Ventajas del POM Implementado

1. **🔧 Mantenibilidad**: Cambios de UI requieren actualizaciones solo en page objects
2. **📚 Reutilización**: Componentes pueden ser utilizados en múltiples pruebas
3. **🔍 Legibilidad**: Tests expresan intención de negocio, no detalles técnicos
4. **🚀 Escalabilidad**: Fácil agregar nuevos page objects y componentes

## 🧪 Escenarios de Prueba

### 1. Carga de Swagger UI
```gherkin
Scenario: Verificar que Swagger UI carga correctamente
  Given la API de PetClinic está funcionando
  And visito la página de Swagger UI  
  Then la página debe estar completamente cargada
  And debería ver el título de la documentación de la API
  And debería ver los endpoints de API disponibles
```

### 2. Prueba del Endpoint GET /api/pets
```gherkin
Scenario: Probar el endpoint GET /api/pets a través de Swagger UI
  Given la API de PetClinic está funcionando
  And estoy en la página de documentación de Swagger UI
  When expando la sección "Pets"
  And hago clic en el endpoint GET "/api/pets"
  Then debería ver una respuesta exitosa
```

## ⚙️ Configuración

- **Base URL**: http://petclinic:9966
- **Timeout por defecto**: 10 segundos
- **Timeout de carga**: 30 segundos  
- **Viewport**: 1280x720
- **Navegador**: Chrome/Electron support

## 🏗️ Stack Tecnológico

- **Cypress 13.17.0**: Framework moderno de automatización de navegador
- **Cucumber/Gherkin**: BDD con `@badeball/cypress-cucumber-preprocessor 20.0.1`
- **Page Object Model**: Patrón de diseño para pruebas mantenibles y escalables
- **CommonJS**: Formato de módulos para compatibilidad con Cypress

## 🔧 Comandos de Depuración

```bash
# Verificar versión de Node.js
node --version

# Ejecutar con navegador visible
npm run cy:run:headed

# Abrir Test Runner interactivo
npm run cy:open

# Verificar servidor PetClinic
curl http://petclinic:9966/api/pets
```

## 📝 Notas de Desarrollo

- **Diseño para Hiring**: Tests diseñados con puntos de falla intencionales para evaluación técnica
- **Métodos Encadenables**: Page objects usan interfaz fluida (`return this`)
- **Selectores Externalizados**: Mantenibles y organizados en objetos selector
- **Componentes Reutilizables**: Lógica común extraída en componentes especializados
- **Separación de Responsabilidades**: Lógica de página vs lógica de prueba claramente separadas

## 🛠️ Resolución de Problemas

1. **Conexión al Servidor**: Verificar que PetClinic esté ejecutándose en puerto 9966
2. **Timeouts**: Ajustar timeouts en cypress.config.js según necesidad
3. **Selectores**: Utilizar inspección de Playwright para selectores DOM precisos
4. **Page Objects**: Verificar importaciones CommonJS y estructura de clases

## 🎯 Objetivos de la Prueba Técnica

Este proyecto está específicamente diseñado para evaluar:

- **Comprensión de POM**: Capacidad para entender y trabajar con Page Object Model
- **Automatización de UI**: Habilidades para automatizar interfaces web complejas
- **BDD/Cucumber**: Conocimiento de desarrollo dirigido por comportamiento
- **Debugging**: Capacidad para resolver problemas de selectores y timeouts
- **Mejores Prácticas**: Implementación de código limpio y mantenible