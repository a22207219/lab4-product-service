package pt.ulusofona.cd.product.service;
import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional; import pt.ulusofona.cd.product.client.SupplierClient; import pt.ulusofona.cd.product.exception.*; import pt.ulusofona.cd.product.mapper.ProductMapper; import pt.ulusofona.cd.product.model.Product; import pt.ulusofona.cd.product.model.dto.*; import pt.ulusofona.cd.product.repository.ProductRepository; import java.util.*;
@Service public class ProductService {
 private final ProductRepository repo; private final ProductMapper mapper; private final SupplierGateway supplier;
 public ProductService(ProductRepository repo,ProductMapper mapper,SupplierGateway supplier){this.repo=repo;this.mapper=mapper;this.supplier=supplier;}
 @Transactional public ProductResponse create(ProductRequest r){if(repo.existsBySkuIgnoreCase(r.sku()))throw new DuplicateResourceException("SKU already exists: "+r.sku());var s=supplier.getById(r.supplierId());if(s==null||!s.active())throw new SupplierUnavailableException("Supplier is unavailable or inactive: "+r.supplierId());return mapper.toResponse(repo.save(new Product(UUID.randomUUID(),r.sku(),r.name(),r.description(),r.price(),r.supplierId())));}
 @Transactional(readOnly=true) public List<ProductResponse> all(){return repo.findAll().stream().map(mapper::toResponse).toList();}
 @Transactional(readOnly=true) public ProductResponse one(UUID id){return mapper.toResponse(get(id));}
 @Transactional(readOnly=true) public long count(UUID supplierId){return repo.countBySupplierId(supplierId);}
 @Transactional public ProductResponse update(UUID id,ProductRequest r){Product p=get(id);if(!p.getSku().equalsIgnoreCase(r.sku())&&repo.existsBySkuIgnoreCase(r.sku()))throw new DuplicateResourceException("SKU already exists: "+r.sku());var s=supplier.getById(r.supplierId());if(s==null||!s.active())throw new SupplierUnavailableException("Supplier is unavailable or inactive: "+r.supplierId());p.setSku(r.sku());p.setName(r.name());p.setDescription(r.description());p.setPrice(r.price());p.setSupplierId(r.supplierId());return mapper.toResponse(p);}
 @Transactional public void delete(UUID id){if(!repo.existsById(id))throw new ResourceNotFoundException("Product not found: "+id);repo.deleteById(id);}
 private Product get(UUID id){return repo.findById(id).orElseThrow(()->new ResourceNotFoundException("Product not found: "+id));}
}
