# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added
- Springdoc OpenAPI Gradle plugin (`org.springdoc.openapi-gradle-plugin` v1.9.0); run `./gradlew generateOpenApiDocs` to generate `docs/openapi.yaml` without a live database
- `application-docs.yml` profile (H2 in-memory) used by the docs generation task to avoid requiring GCP Cloud SQL locally
- `src/test/resources/application.yml` that disables `spring-cloud-gcp-starter-sql-mysql` auto-configuration and substitutes H2 for all Spring test contexts, preventing startup failures when GCP credentials are not available
- Unit tests for `ProductService` covering: successful retrieval, not-found exception, full update, partial update (null fields), and update on non-existent product
- Integration tests for `ProductController` (`@WebMvcTest`) covering: successful GET and PATCH, path variable length validation (400), unknown JSON field rejection (400), `descripcion` max-length validation (400), and 404 handling

### Added
- REST endpoint `GET /articulos/{id}` to retrieve a product by its 10-character ID, returning 404 if not found
- REST endpoint `PATCH /articulos/{id}` for partial update of `descripcion` and `modelo` fields; returns 400 if immutable fields (`id`, `nombre`, `precio`) or unknown fields are sent
- `Product` JPA entity mapped to `products` table in GCP Cloud SQL (MySQL); immutable fields (`id`, `name`, `price`) protected with `updatable = false`
- Jackson global configuration `fail-on-unknown-properties: true` to enforce `additionalProperties: false` contract from OpenAPI spec
- Springdoc OpenAPI UI accessible at `/swagger-ui.html` with full endpoint documentation via meta-annotations
- Global exception handler (`GlobalExceptionHandler`) for structured `ProblemDetail` error responses
- Multi-stage `Dockerfile` using Gradle 8.12 + JDK 21 for build and `eclipse-temurin:21-jre-alpine` for runtime
- GCP Cloud SQL connection configured via environment variables (`DB_INSTANCE_CONNECTION_NAME`, `DB_NAME`, `DB_USER`, `DB_PASSWORD`) for Cloud Run compatibility
- JSON structured logging via `logback-spring.xml` with GCP Stackdriver layout for Cloud Logging compatibility
- Virtual threads enabled (`spring.threads.virtual.enabled: true`) for improved concurrency under Java 21
