package pt.ulusofona.cd.product.model.dto;
import java.math.BigDecimal; import java.time.Instant; import java.util.UUID;
public record ProductResponse(UUID id,String sku,String name,String description,BigDecimal price,UUID supplierId,Instant createdAt,Instant updatedAt,long version) {}
