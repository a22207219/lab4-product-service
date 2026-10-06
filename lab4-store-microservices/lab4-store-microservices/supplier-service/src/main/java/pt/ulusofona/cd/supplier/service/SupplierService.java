package pt.ulusofona.cd.supplier.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.ulusofona.cd.supplier.exception.ResourceNotFoundException;
import pt.ulusofona.cd.supplier.mapper.SupplierMapper;
import pt.ulusofona.cd.supplier.model.Supplier;
import pt.ulusofona.cd.supplier.model.dto.SupplierRequest;
import pt.ulusofona.cd.supplier.model.dto.SupplierResponse;
import pt.ulusofona.cd.supplier.repository.SupplierRepository;

import java.util.List;
import java.util.UUID;

@Service
public class SupplierService {
    private final SupplierRepository repo;
    private final SupplierMapper mapper;
    private final ProductGateway products;

    public SupplierService(SupplierRepository repo, SupplierMapper mapper, ProductGateway products) {
        this.repo = repo;
        this.mapper = mapper;
        this.products = products;
    }

    @Transactional
    public SupplierResponse create(SupplierRequest request) {
        Supplier supplier = new Supplier(
                null, request.name(), request.email(), request.phone(),
                request.address(), request.active());
        supplier = repo.save(supplier);

        // A newly created supplier has no products yet, so creation does not
        // need a network call back to product-service.
        return mapper.toResponse(supplier, 0);
    }

    @Transactional(readOnly = true)
    public List<SupplierResponse> all() {
        return repo.findAll().stream()
                .map(s -> mapper.toResponse(s, products.count(s.getId())))
                .toList();
    }

    @Transactional(readOnly = true)
    public SupplierResponse one(UUID id) {
        Supplier supplier = get(id);
        return mapper.toResponse(supplier, products.count(id));
    }

    @Transactional
    public SupplierResponse update(UUID id, SupplierRequest request) {
        Supplier supplier = get(id);
        supplier.setName(request.name());
        supplier.setEmail(request.email());
        supplier.setPhone(request.phone());
        supplier.setAddress(request.address());
        supplier.setActive(request.active());
        return mapper.toResponse(supplier, products.count(id));
    }

    @Transactional
    public void delete(UUID id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Supplier not found: " + id);
        }
        repo.deleteById(id);
    }

    private Supplier get(UUID id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found: " + id));
    }
}
