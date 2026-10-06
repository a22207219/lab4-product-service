package pt.ulusofona.cd.product.model.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal; import java.util.UUID;
public record ProductRequest(@NotBlank @Size(max=50) String sku,@NotBlank @Size(max=120) String name,@Size(max=500) String description,@NotNull @DecimalMin("0.0") BigDecimal price,@NotNull UUID supplierId) {}
