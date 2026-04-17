# Gapsi Product Catalog API 🚀

## Overview
Este proyecto es una implementación robusta de una API RESTful para la gestión de un catálogo de artículos, diseñada específicamente para ser ejecutada en un entorno **Cloud-Native** dentro de **Google Cloud Platform (GCP)**.

La solución prioriza la **seguridad, la inmutabilidad de los datos de negocio y la observabilidad**, utilizando estándares modernos de desarrollo con Java 21 y Spring Boot 3.x.

---

## 🏗️ Architecture & Design
Se ha aplicado **Clean Architecture** para separar las responsabilidades de forma clara:
- **API Layer:** Controladores REST con validaciones de entrada estrictas y documentación OpenAPI.
- **Service Layer:** Lógica de negocio y orquestación. Se implementó una lógica de actualización parcial (**PATCH**) para proteger la integridad de los datos.
- **Domain Layer:** Entidades JPA con restricciones de inmutabilidad (`updatable = false`).
- **Infrastructure Layer:** Configuración de persistencia y conectividad nativa con GCP.

### Key Technical Decisions
- **Java 21 Virtual Threads:** Optimización para alta concurrencia en entornos Serverless (Cloud Run).
- **Inmutabilidad:** Siguiendo el requerimiento, los campos `id`, `name` y `price` están protegidos contra actualizaciones a nivel de JPA y DTO.
- **GCP Cloud SQL Connector:** Conexión segura mediante IAM, evitando la exposición de IPs públicas.
- **Docker Multi-stage:** Construcción optimizada para reducir el tamaño de la imagen y el tiempo de arranque.

---

## 🛠️ Tech Stack
- **Language:** Java 21
- **Framework:** Spring Boot 3.5.x
- **Build Tool:** Gradle
- **Database:** Cloud SQL (MySQL 8.0)
- **Cloud Infrastructure:** Google Cloud Run (Serverless)
- **Documentation:** OpenAPI 3.0 / Swagger UI (Springdoc)
- **Testing:** JUnit 5, Mockito, MockMvc

---

## 🚀 Deployment & Infrastructure
El servicio está diseñado para desplegarse en **Cloud Run**. 

### Requisitos de Infraestructura en GCP:
1. **Cloud SQL Instance:** Base de datos MySQL con el esquema definido en `src/main/resources/schema.sql`.
2. **IAM Roles:** La cuenta de servicio de Cloud Run requiere el rol `Cloud SQL Client`.
3. **Artifact Registry:** Repositorio para la imagen Docker del microservicio.

### Running Locally
1. Clonar el repositorio.
2. Configurar las variables de entorno para la base de datos (o usar el perfil `test` para H2).
3. Ejecutar: `./gradlew bootRun`

---

## 📖 API Documentation
Una vez iniciada la aplicación, la documentación interactiva (Swagger UI) está disponible en:
`https://<your-url>/swagger-ui.html`

También se incluye el archivo `openapi.yaml` en la raíz del proyecto con la definición estática del contrato.

---

## ✅ Business Rules Validation
| Field | Type | Constraint | Updatable |
| :--- | :--- | :--- | :--- |
| `id` | Alphanumeric (10) | Primary Key | ❌ No |
| `name` | Alphanumeric (20) | Not Null | ❌ No |
| `description` | Alphanumeric (200) | Optional | ✅ Yes |
| `price` | Decimal (10,2) | Not Null | ❌ No |
| `model` | Alphanumeric (10) | Optional | ✅ Yes |

---

## 📝 Author
**Javier** - Lead Backend Developer / Cloud Architect
