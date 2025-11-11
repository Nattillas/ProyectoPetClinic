# PetClinic - Hexagonal Architecture

Un mini proyecto Java con Spring Boot que implementa el OpenAPI de PetClinic siguiendo los principios de la arquitectura hexagonal.

## 🏗️ Arquitectura

Este proyecto implementa una **arquitectura hexagonal** (también conocida como "Ports and Adapters") que separa claramente:

### Capas de la Aplicación

```
📦 Hexagonal Architecture
├── 🏛️ Domain (Core Business Logic)
│   ├── 📋 Models (Pet, Owner, PetType, Visit)
│   ├── 🔌 Ports (Repository Interfaces)
│   └── 🧠 Services (Business Logic)
├── 🏗️ Infrastructure (External Concerns)
│   ├── 🗄️ Entities (JPA Entities)
│   ├── 📊 Repositories (JPA Repositories)
│   └── 🔌 Adapters (Port Implementations)
└── 🌐 Application (User Interface)
    ├── 🎮 Controllers (REST APIs)
    ├── 📦 DTOs (Data Transfer Objects)
    └── 🗺️ Mappers (DTO Converters)
```

### Principios Aplicados

- **Inversión de Dependencias**: El dominio define interfaces (puertos) que la infraestructura implementa
- **Separación de Responsabilidades**: Cada capa tiene una responsabilidad específica
- **Independencia de Frameworks**: La lógica de negocio no depende de Spring o JPA
- **Testabilidad**: Fácil testing usando mocks para los puertos

## 🚀 Características

- ✅ API REST completa para gestión de mascotas
- ✅ Documentación OpenAPI/Swagger
- ✅ Validación de datos con Bean Validation
- ✅ Base de datos H2 en memoria
- ✅ Tests unitarios con Mockito
- ✅ Tests de integración
- ✅ Arquitectura hexagonal

## 📋 Requisitos

- Java 17+
- Maven 3.6+

## 🛠️ Instalación y Ejecución

### 1. Clonar y Compilar

```bash
cd /home/davidcag/wslworkspace/hiring-qa-2025/code
mvn clean compile
```

### 2. Ejecutar Tests

```bash
mvn test
```

### 3. Ejecutar la Aplicación

```bash
mvn spring-boot:run
```

La aplicación estará disponible en: http://localhost:8080/petclinic (http://localhost:8080/petclinic/swagger-ui/index.html)

## 📚 APIs Disponibles

### Swagger UI
- **URL**: http://localhost:8080/petclinic/swagger-ui.html
- **API Docs**: http://localhost:8080/petclinic/api-docs

### Endpoints Principales

#### Mascotas
- `GET /api/pets` - Obtener todas las mascotas
- `GET /api/pets/{id}` - Obtener mascota por ID
- `POST /api/pets` - Crear nueva mascota
- `PUT /api/pets/{id}` - Actualizar mascota
- `DELETE /api/pets/{id}` - Eliminar mascota
- `GET /api/pets/search?name={name}` - Buscar mascotas por nombre
- `GET /api/pets/owner/{ownerId}` - Obtener mascotas por dueño

### H2 Console (Desarrollo)
- **URL**: http://localhost:8080/petclinic/h2-console
- **JDBC URL**: `jdbc:h2:mem:petclinic`
- **Usuario**: `sa`
- **Contraseña**: (vacía)

## 🧪 Testing

El proyecto incluye tests unitarios y de integración

### Cobertura
Los tests cubren:
- ✅ Lógica de negocio en servicios
- ✅ Validaciones de dominio
- ✅ Controladores REST
- ✅ Manejo de errores
- ✅ Mapeo de DTOs

## 🏗️ Estructura del Proyecto

```
src/
├── main/java/com/petclinic/
│   ├── domain/                    # 🏛️ Capa de Dominio
│   │   ├── model/                # Entidades de dominio
│   │   ├── port/                 # Interfaces (puertos)
│   │   └── service/              # Lógica de negocio
│   ├── infrastructure/           # 🏗️ Capa de Infraestructura
│   │   ├── adapter/              # Implementaciones de puertos
│   │   ├── entity/               # Entidades JPA
│   │   └── repository/           # Repositorios JPA
│   ├── application/              # 🌐 Capa de Aplicación
│   │   ├── controller/           # Controladores REST
│   │   ├── dto/                  # DTOs
│   │   └── mapper/               # Mappers DTO-Domain
│   ├── config/                   # ⚙️ Configuración
│   └── PetClinicApplication.java # 🚀 Clase principal
├── test/java/                    # 🧪 Tests
├── resources/
│   ├── application.yml           # Configuración
│   └── data.sql                  # Datos de prueba
```