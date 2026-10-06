package pt.ulusofona.cd.supplier.model.dto;
import jakarta.validation.constraints.*;
public record SupplierRequest(@NotBlank @Size(max=150) String name,@NotBlank @Email @Size(max=254) String email,@Size(max=30) String phone,@Size(max=300) String address,boolean active) {}
