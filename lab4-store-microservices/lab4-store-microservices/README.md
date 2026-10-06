# Lab 4 — Splitting the Monolith

**Student:** Carlos Centeio  
**Student number:** a22207219

This is the Lab 4 multi-module project for Distributed Systems 2026/27. It splits the Lab 3 monolith along the data boundary into two independently deployable services.

## Architecture

| Service | Port | Package | Database | Owns |
|---|---:|---|---|---|
| product-service | 8081 | `pt.ulusofona.cd.product` | `product_db` | `product` |
| supplier-service | 8082 | `pt.ulusofona.cd.supplier` | `supplier_db` | `supplier` |

There are exactly four runtime containers: the two Spring Boot applications and one PostgreSQL 18 container for each service. No service has credentials for the other service's database.

## Cross-service calls

- product-service → supplier-service: checks that a supplier exists and is active before creating/updating a product.
- supplier-service → product-service: obtains the current number of products supplied by a supplier when returning supplier details/listings. Creating a supplier does not depend on product-service being available.
- Both clients use Spring `@HttpExchange` backed by `RestClient`. No Spring Cloud or OpenFeign is used.
- Service base URLs are supplied through environment/configuration, not hard-coded in the clients.

## Resilience

Both HTTP clients have: connection/read timeouts, Resilience4j retry with backoff, a circuit breaker and fallback/error handling. The Boot 4 AspectJ starter is included so the Resilience4j annotations are actually applied.

When the remote service is unavailable, the API returns HTTP 503 with a `ProblemDetail` response rather than hanging indefinitely.

## Run on Windows

1. Make sure Docker Desktop is running and has at least 4 GB available.
2. From this directory:

```powershell
Copy-Item .env.example .env
mvnw.cmd clean verify
docker compose up --build
```

The course toolchain uses Java 25, Spring Boot 4.1.0, PostgreSQL 18, Maven 3.9.16 and Docker Compose v2 syntax. The repository must be built with the committed Maven wrapper (`mvnw.cmd` on Windows).

## Health checks

```powershell
curl.exe http://localhost:8081/actuator/health
curl.exe http://localhost:8082/actuator/health
docker compose ps
```

All four containers should become `healthy`.

## End-to-end exercise

After the stack is running:

```powershell
.\scripts\smoke-test.ps1
```

This exercises both HTTP directions: supplier creation/count and product creation with the supplier active check.

To exercise retry/fallback/circuit-breaker behaviour:

```powershell
.\scripts\resilience-test.ps1
```

The script temporarily stops `supplier-service`, performs four product-create calls and records the HTTP status and response time, then starts the service again.

## Endpoints

Product service:
- `GET/POST /api/v1/products`
- `GET/PUT/DELETE /api/v1/products/{id}`
- `GET /api/v1/products/count?supplierId={id}`

Supplier service:
- `GET/POST /api/v1/suppliers`
- `GET/PUT/DELETE /api/v1/suppliers/{id}`

## Project structure

```text
store-microservices/
├── pom.xml
├── docker-compose.yml
├── .env.example
├── product-service/
│   ├── pom.xml
│   └── src/main/java/pt/ulusofona/cd/product/
└── supplier-service/
    ├── pom.xml
    └── src/main/java/pt/ulusofona/cd/supplier/
```

## Important

Do not commit `.env`. It is ignored by Git. Commit `.env.example` instead.

## Maven wrapper

`mvnw`, `mvnw.cmd` and `.mvn/wrapper/maven-wrapper.properties` are committed. The wrapper bootstraps Maven 3.9.16 from the official Apache Maven distribution when it is not already cached.

## Course package convention

Each service keeps the course layer structure under its own root package. The domain entities and the course-wide `Identifiable` contract live under `.model`; DTOs are under `.model.dto`; persistence is under `.repository`; business logic is under `.service`; HTTP controllers are under `.controller`; mapping is under `.mapper`; exceptions are under `.exception`; HTTP client configuration is under `.config` and the declarative HTTP interfaces are under `.client`.
