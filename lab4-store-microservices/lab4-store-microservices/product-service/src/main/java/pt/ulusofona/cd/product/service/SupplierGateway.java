package pt.ulusofona.cd.product.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import pt.ulusofona.cd.product.client.SupplierClient;
import pt.ulusofona.cd.product.exception.SupplierUnavailableException;
import pt.ulusofona.cd.product.exception.SupplierNotFoundException;

import java.util.UUID;

@Service
public class SupplierGateway {
    private final SupplierClient client;

    public SupplierGateway(SupplierClient client) {
        this.client = client;
    }

    @Retry(name = "supplierService", fallbackMethod = "fallback")
    @CircuitBreaker(name = "supplierService")
    public SupplierClient.SupplierResponse getById(UUID id) {
        return client.getById(id);
    }

    private SupplierClient.SupplierResponse fallback(UUID id, Throwable cause) {
        if (cause instanceof HttpClientErrorException.NotFound) {
            throw new SupplierNotFoundException("Supplier not found: " + id);
        }
        throw new SupplierUnavailableException("supplier-service is unavailable: " + cause.getClass().getSimpleName());
    }
}
