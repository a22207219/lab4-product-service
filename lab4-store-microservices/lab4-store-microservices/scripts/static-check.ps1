$ErrorActionPreference = "Stop"
$root = Split-Path -Parent $PSScriptRoot

$required = @(
  "pom.xml",
  "mvnw",
  "mvnw.cmd",
  ".mvn/wrapper/maven-wrapper.properties",
  "docker-compose.yml",
  ".env.example",
  ".gitignore",
  "product-service/pom.xml",
  "supplier-service/pom.xml",
  "product-service/src/main/resources/db/migration/V1__create_product.sql",
  "supplier-service/src/main/resources/db/migration/V1__create_supplier.sql"
)

foreach ($path in $required) {
  if (-not (Test-Path (Join-Path $root $path))) {
    throw "Missing required file: $path"
  }
}

if (Test-Path (Join-Path $root ".env")) {
  throw ".env must not be committed in the project archive"
}

$compose = Get-Content (Join-Path $root "docker-compose.yml") -Raw
$source = (Get-ChildItem -Path $root -Recurse -File -Include *.java,*.xml,*.yml,*.yaml | Get-Content -Raw) -join "`n"

foreach ($needle in @(
  "@HttpExchange",
  "RestClientAdapter",
  "HttpServiceProxyFactory",
  "@Retry",
  "@CircuitBreaker",
  "ProblemDetail"
)) {
  if ($source -notmatch [regex]::Escape($needle)) {
    throw "Missing required feature: $needle"
  }
}

foreach ($forbidden in @(
  "spring-cloud-dependencies",
  "spring-cloud-starter-openfeign",
  "@EnableFeignClients"
)) {
  if ($source -match [regex]::Escape($forbidden)) {
    throw "Forbidden Spring Cloud/OpenFeign feature detected: $forbidden"
  }
}

if ($compose -match "^version\s*:") {
  throw "Compose must use v2 syntax without a version key"
}

Write-Host "Lab 4 static checks passed."
Write-Host "Run mvnw.cmd clean verify and docker compose up --build for runtime verification."
