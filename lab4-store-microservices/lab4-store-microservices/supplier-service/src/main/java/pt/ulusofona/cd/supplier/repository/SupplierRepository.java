package pt.ulusofona.cd.supplier.repository;
import org.springframework.data.jpa.repository.JpaRepository; import pt.ulusofona.cd.supplier.model.Supplier; import java.util.UUID;
public interface SupplierRepository extends JpaRepository<Supplier,UUID>{}
