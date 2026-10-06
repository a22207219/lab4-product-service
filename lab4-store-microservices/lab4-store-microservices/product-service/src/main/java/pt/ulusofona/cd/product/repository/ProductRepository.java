package pt.ulusofona.cd.product.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import pt.ulusofona.cd.product.model.Product;
import java.util.UUID;
public interface ProductRepository extends JpaRepository<Product,UUID>{long countBySupplierId(UUID supplierId);boolean existsBySkuIgnoreCase(String sku);}
