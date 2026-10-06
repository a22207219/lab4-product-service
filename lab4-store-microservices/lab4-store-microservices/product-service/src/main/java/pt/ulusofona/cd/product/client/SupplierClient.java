package pt.ulusofona.cd.product.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.UUID;

@HttpExchange("/api/v1/suppliers")
public interface SupplierClient {

    @GetExchange("/{id}")
    SupplierResponse getById(@PathVariable("id") UUID id);

    record SupplierResponse(UUID id, String name, String email, String phone, String address, boolean active) {}
}
