# Lab 4 — Final compliance checklist

This archive was reviewed against the official 2026/27 Lab 4 page: **Lab 4: Splitting the Monolith**.

## Required architecture

- [x] Maven multi-module project `pt.ulusofona.cd:store-microservices`
- [x] `product-service` on port 8081, package `pt.ulusofona.cd.product`
- [x] `supplier-service` on port 8082, package `pt.ulusofona.cd.supplier`
- [x] Product owns the `product` table
- [x] Supplier owns the `supplier` table
- [x] One PostgreSQL 18 database per service
- [x] Separate Flyway V1 migration for each database
- [x] No cross-database credentials
- [x] No cross-database JOINs

## Cross-service HTTP

- [x] Product → Supplier with `@HttpExchange`
- [x] Supplier → Product with `@HttpExchange`
- [x] `RestClient`
- [x] `RestClientAdapter`
- [x] `HttpServiceProxyFactory`
- [x] Base URLs come from configuration/environment

## Resilience

- [x] HTTP connect timeout
- [x] HTTP read timeout
- [x] Retry with exponential backoff
- [x] Fallback
- [x] Circuit breaker
- [x] Circuit-breaker-open failures fail fast
- [x] Supplier 404 remains a 404 instead of becoming a service outage
- [x] Remote-service outage becomes HTTP 503 with `ProblemDetail`
- [x] Actuator metrics exposed
- [x] Resilience exercise script included

## Docker

- [x] One Compose stack
- [x] Exactly four runtime containers
- [x] PostgreSQL healthchecks use the matching database/user
- [x] Application healthchecks use `/actuator/health`
- [x] No `version:` key in Compose
- [x] Runtime application images contain `curl` for their healthchecks

## Course conventions

- [x] Java 25
- [x] Spring Boot 4.1.0
- [x] PostgreSQL 18
- [x] Maven wrapper targeting 3.9.16
- [x] `application.yml` only
- [x] `Identifiable` lives under each service's `.model` package
- [x] `.env` ignored and not included in the archive
- [x] No Spring Cloud/OpenFeign dependency

## Included verification helpers

- `scripts/static-check.ps1` — structural/configuration checks
- `scripts/validate-compose.ps1` — runs `docker compose config`
- `scripts/smoke-test.ps1` — exercises both cross-service directions
- `scripts/resilience-test.ps1` — exercises remote-service failure and resilience behaviour

## Runtime verification

The final archive was statically audited here, including XML/YAML parsing and source-level checks. Docker is not installed in the preparation environment, so the following cannot honestly be marked as executed here:

```powershell
./mvnw.cmd clean verify
docker compose up --build
docker compose ps
./scripts/smoke-test.ps1
./scripts/resilience-test.ps1
```

Those commands are intentionally left as the final clean-machine verification because the course rubric itself requires the project to work from a clean clone.
