$ErrorActionPreference = "Stop"
docker compose config | Out-Null
Write-Host "docker-compose.yml syntax OK"
