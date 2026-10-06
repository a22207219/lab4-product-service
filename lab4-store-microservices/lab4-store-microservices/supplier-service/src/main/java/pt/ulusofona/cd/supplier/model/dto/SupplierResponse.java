package pt.ulusofona.cd.supplier.model.dto;
import java.time.Instant; import java.util.UUID;
public record SupplierResponse(UUID id,String name,String email,String phone,String address,boolean active,Instant createdAt,Instant updatedAt,long version,long productCount) {}
