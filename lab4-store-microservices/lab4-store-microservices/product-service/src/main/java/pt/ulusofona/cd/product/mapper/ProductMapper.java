package pt.ulusofona.cd.product.mapper;
import org.springframework.stereotype.Component; import pt.ulusofona.cd.product.model.Product; import pt.ulusofona.cd.product.model.dto.ProductResponse;
@Component public class ProductMapper { public ProductResponse toResponse(Product p){return new ProductResponse(p.getId(),p.getSku(),p.getName(),p.getDescription(),p.getPrice(),p.getSupplierId(),p.getCreatedAt(),p.getUpdatedAt(),p.getVersion());} }
