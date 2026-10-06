CREATE TABLE product (
 id UUID PRIMARY KEY,
 sku VARCHAR(50) NOT NULL,
 name VARCHAR(120) NOT NULL,
 description VARCHAR(500),
 price NUMERIC(19,2) NOT NULL,
 supplier_id UUID NOT NULL,
 created_at TIMESTAMPTZ NOT NULL,
 updated_at TIMESTAMPTZ NOT NULL,
 version BIGINT NOT NULL
);
CREATE UNIQUE INDEX ux_product_sku_lower ON product (LOWER(sku));
CREATE INDEX ix_product_supplier_id ON product (supplier_id);
