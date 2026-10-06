package pt.ulusofona.cd.supplier.mapper;
import org.springframework.stereotype.Component; import pt.ulusofona.cd.supplier.model.Supplier; import pt.ulusofona.cd.supplier.model.dto.SupplierResponse;
@Component public class SupplierMapper { public SupplierResponse toResponse(Supplier s,long count){return new SupplierResponse(s.getId(),s.getName(),s.getEmail(),s.getPhone(),s.getAddress(),s.isActive(),s.getCreatedAt(),s.getUpdatedAt(),s.getVersion(),count);} }
