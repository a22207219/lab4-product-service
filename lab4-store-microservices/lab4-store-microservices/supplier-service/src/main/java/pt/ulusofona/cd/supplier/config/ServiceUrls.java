package pt.ulusofona.cd.supplier.config;
import org.springframework.boot.context.properties.ConfigurationProperties;
@ConfigurationProperties(prefix="services") public record ServiceUrls(String productBaseUrl) {}
