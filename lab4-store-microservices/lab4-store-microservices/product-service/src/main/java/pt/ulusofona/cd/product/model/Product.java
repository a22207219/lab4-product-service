package pt.ulusofona.cd.product.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name="product")
public class Product implements Identifiable {
    @Id @Column(nullable=false, updatable=false) private UUID id;
    @Column(nullable=false, unique=true, length=50) private String sku;
    @Column(nullable=false, length=120) private String name;
    @Column(length=500) private String description;
    @Column(nullable=false, precision=19, scale=2) private BigDecimal price;
    @Column(name="supplier_id", nullable=false) private UUID supplierId;
    @Column(name="created_at", nullable=false, updatable=false) private Instant createdAt;
    @Column(name="updated_at", nullable=false) private Instant updatedAt;
    @Version @Column(nullable=false) private long version;
    protected Product() {}
    public Product(UUID id,String sku,String name,String description,BigDecimal price,UUID supplierId){this.id=id;this.sku=sku;this.name=name;this.description=description;this.price=price;this.supplierId=supplierId;}
    @PrePersist void create(){Instant now=Instant.now();if(id==null)id=UUID.randomUUID();createdAt=now;updatedAt=now;}
    @PreUpdate void update(){updatedAt=Instant.now();}
    public UUID getId(){return id;} public String getSku(){return sku;} public void setSku(String v){sku=v;} public String getName(){return name;} public void setName(String v){name=v;} public String getDescription(){return description;} public void setDescription(String v){description=v;} public BigDecimal getPrice(){return price;} public void setPrice(BigDecimal v){price=v;} public UUID getSupplierId(){return supplierId;} public void setSupplierId(UUID v){supplierId=v;} public Instant getCreatedAt(){return createdAt;} public Instant getUpdatedAt(){return updatedAt;} public long getVersion(){return version;}
}
