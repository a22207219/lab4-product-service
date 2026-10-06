package pt.ulusofona.cd.supplier.client;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.UUID;

@HttpExchange("/api/v1/products")
public interface ProductClient {

    @GetExchange("/count")
    long count(@RequestParam("supplierId") UUID supplierId);
}
