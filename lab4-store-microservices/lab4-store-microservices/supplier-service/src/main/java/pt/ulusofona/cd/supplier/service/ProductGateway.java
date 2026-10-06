package pt.ulusofona.cd.supplier.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;
import pt.ulusofona.cd.supplier.client.ProductClient;
import pt.ulusofona.cd.supplier.exception.ProductServiceUnavailableException;

import java.util.UUID;

@Service
public class ProductGateway {
    private final ProductClient client;

    public ProductGateway(ProductClient client) {
        this.client = client;
    }

    @Retry(name = "productService", fallbackMethod = "fallback")
    @CircuitBreaker(name = "productService")
    public long count(UUID supplierId) {
        return client.count(supplierId);
    }

    private long fallback(UUID supplierId, Throwable cause) {
        throw new ProductServiceUnavailableException("product-service is unavailable: " + cause.getClass().getSimpleName());
    }
}
