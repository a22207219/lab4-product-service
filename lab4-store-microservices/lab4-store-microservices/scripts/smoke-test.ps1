$ErrorActionPreference = "Stop"
$baseProduct = "http://localhost:8081"
$baseSupplier = "http://localhost:8082"

Write-Host "1. Creating supplier..."
$supplierBody = @{ name="Demo Supplier"; email="demo@example.com"; phone="210000000"; address="Lisbon"; active=$true } | ConvertTo-Json
$supplier = Invoke-RestMethod -Method Post -Uri "$baseSupplier/api/v1/suppliers" -ContentType "application/json" -Body $supplierBody
$supplierId = $supplier.id
Write-Host "Supplier: $supplierId"

Write-Host "2. Creating product through product-service (cross-service supplier check)..."
$productBody = @{ sku="LAB4-DEMO-001"; name="Demo Product"; description="Lab 4 smoke test"; price=19.99; supplierId=$supplierId } | ConvertTo-Json
$product = Invoke-RestMethod -Method Post -Uri "$baseProduct/api/v1/products" -ContentType "application/json" -Body $productBody
Write-Host "Product: $($product.id)"

Write-Host "3. Reading supplier (cross-service product count)..."
$result = Invoke-RestMethod -Method Get -Uri "$baseSupplier/api/v1/suppliers/$supplierId"
Write-Host "Product count: $($result.productCount)"

if ($result.productCount -lt 1) { throw "Expected productCount >= 1" }
Write-Host "Smoke test OK"
