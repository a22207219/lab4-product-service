package pt.ulusofona.cd.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import pt.ulusofona.cd.product.config.ServiceUrls;

@SpringBootApplication
@EnableConfigurationProperties(ServiceUrls.class)
public class ProductServiceApplication {
    public static void main(String[] args) { SpringApplication.run(ProductServiceApplication.class, args); }
}
