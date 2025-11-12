# Proyecto PetClinic

Este repositorio contiene un mini proyecto Java con Spring Boot que implementa el OpenAPI de PetClinic y un framework de pruebas End-to-End (Karate, Java-Cucumber y Cypress).

## 📈 Acciones requeridas

- [ ] Repasar la estructura de proyecto y leer los READMEs para mayor claridad.
- [ ] Verificar que el proyecto se puede ejecutar y arrancar correctamente
- [ ] Generar una colección de Postman o similar en la que ejecutemos una (1) llamada a la API (adjuntar colección, script o captura de pantalla)
- [ ] Verificar cuantos tests **unitarios** y de **integración** se están ejecutando y cual es su resultado. Aportar evidencias (reporte, captura de pantalla,etc.)
- [ ] Definir un (1) escenario en Karate **O** java-cucumber (el que se prefiera) que cubra un flujo básico de la aplicación para pruebas E2E de backend.
      
      * Ej: *Verificar que si añadimos una mascota vía API, podemos recuperarla después vía API o vía consulta SQL*
- [ ] Completar un (1) escenario de Cypress donde verifiquemos que podemos interactuar con la UI de Swagger (demo) para simular un escenario de test contra una aplicación web (frontend).
      
      * Ej: *Obtener el listado de mascotas*

 | !! La solución no tiene un tiempo límite. No obstante, completar las validaciones e implementar los escenarios (RECUERDA que sólo se pide 1 escenario para e2e backend y 1 para frontend) se puede completar en 25 minutos.

## 📦 Entregables

Una vez completada la prueba puede hacernos llegar los cambios introducidos de una de las siguientes formas:

 - **A)** *(Preferible)* Cree un repositorio propio en Github. Suba el código y remítanos el link. Es importante que el repositorio sea público.
 - **B)** Comprima el proyecto en .zip y reenvíenos la prueba. (No olvide eliminar las carpetas */target* y */node_modules* para reducir el tamaño del mismo)
 
## 📁 Estructura del Proyecto

```
.
├── code/                    # Aplicación principal PetClinic Spring Boot
│   ├── src/                 # Código fuente de la aplicación
│   ├── pom.xml              # Configuración Maven
│   └── README.md            # Documentación específica de la aplicación
│
├── e2e/                     # Frameworks de Pruebas End-to-End
│   ├── karate/              # Framework de pruebas API con Karate
│   │   ├── src/test/        # Escenarios de pruebas E2E
│   │   ├── pom.xml          # Configuración Maven del framework Karate
│   │   └── README.md        # Documentación de pruebas Karate
│   │
│   ├── cypress/             # Framework de pruebas UI con Cypress
│   │   ├── cypress/         # Tests E2E con Page Object Model y Cucumber BDD
│   │   ├── package.json     # Dependencias Node.js
│   │   └── README.md        # Documentación de pruebas Cypress
│   │
│   └── java-cucumber/       # Framework de pruebas API con Java Cucumber
│       ├── src/test/        # Escenarios de pruebas Cucumber y definiciones de pasos
│       ├── pom.xml          # Configuración Maven del framework Cucumber
│       └── README.md        # Documentación de pruebas Java Cucumber
│
└── README.md                # Este archivo - Visión general del proyecto
```

## 🚀 Inicio Rápido

### 1. Ejecutar la Aplicación

```bash
cd code/
mvn install
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080/petclinic`

### 2. Ejecutar Pruebas E2E

#### Opción A: Framework Karate
```bash
cd e2e/karate/
mvn test
```

#### Opción B: Framework Java Cucumber  
```bash
cd e2e/java-cucumber/
mvn test
```

#### Opción C: Framework Cypress (UI Testing)
```bash
cd e2e/cypress/
npm install
npm test (consola) 
npm run cypress:open (interfaz)
```

## 📖 Documentación

- **Documentación de la Aplicación**: Ver `code/README.md` para información detallada sobre la aplicación PetClinic Spring Boot
- **Pruebas Karate**: Ver `e2e/karate/README.md` para información completa sobre el framework de pruebas E2E con Karate
- **Pruebas Cypress**: Ver `e2e/cypress/README.md` para información completa sobre el framework de pruebas UI con Cypress, Page Object Model y Cucumber BDD
- **Pruebas Java Cucumber**: Ver `e2e/java-cucumber/README.md` para información completa sobre el framework de pruebas E2E con Java Cucumber

## 🧪 Flujo de Desarrollo

1. **Desarrollo de la Aplicación**: Directorio `code/`. El proyecto es 100% funcional y no se requiren modificaciones. 
2. **Pruebas E2E**: Validar cambios usando las pruebas en el directorio `e2e/`
   - **Framework Karate**: Pruebas BDD basadas en Karate
   - **Framework Cypress**: Pruebas de UI automatizadas con navegador real, Page Object Model y Cucumber BDD
   - **Java Cucumber**: Pruebas BDD basadas en Java-Cucumber con RestAssured

## 🔧 Requisitos

- Java 17 o superior
- Maven 3.6 o superior
- Node.js 20.11.0 o superior (para pruebas Cypress)
- Git

## ❓ Dudas

- Si tienes cualquier duda puedes enviarlas a david.castroaguilar@plexus.es e intentaré resolverla a la mayor brevedad posible
- Si surgen problemas de configuración con las herramientas, compilación, etc. háznoslo saber
- En caso de no poder completar cualquiera de los escenarios se valorarán los puntos resueltos o las alternativas aportadas