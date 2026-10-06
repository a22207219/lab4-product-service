$ErrorActionPreference = "Stop"
$uri = "http://localhost:8081/api/v1/products"
$body = @{ sku="LAB4-FAILURE-$(Get-Random)"; name="Failure test"; description="Expected failure"; price=1.00; supplierId=[guid]::NewGuid() } | ConvertTo-Json

Write-Host "Stopping supplier-service to exercise retry/fallback/circuit breaker..."
docker compose stop supplier-service | Out-Host
try {
  for ($i=1; $i -le 4; $i++) {
    $sw=[Diagnostics.Stopwatch]::StartNew()
    try {
      Invoke-WebRequest -Method Post -Uri $uri -ContentType "application/json" -Body $body -UseBasicParsing | Out-Null
      Write-Host "Call $i unexpectedly succeeded"
    } catch {
      $status=$_.Exception.Response.StatusCode.value__
      $sw.Stop()
      Write-Host "Call $i -> HTTP $status in $($sw.Elapsed.TotalSeconds.ToString('0.00'))s"
    }
  }
} finally {
  docker compose start supplier-service | Out-Host
}
Write-Host "Resilience exercise finished. Expect HTTP 503 and bounded response times."
Write-Host "Circuit-breaker metrics:"
curl.exe -fsS http://localhost:8081/actuator/metrics/resilience4j.circuitbreaker.calls | Write-Output
