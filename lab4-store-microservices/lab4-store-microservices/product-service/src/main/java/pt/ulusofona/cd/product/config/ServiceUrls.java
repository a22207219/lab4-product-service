package pt.ulusofona.cd.product.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "services")
public record ServiceUrls(String supplierBaseUrl) {}
