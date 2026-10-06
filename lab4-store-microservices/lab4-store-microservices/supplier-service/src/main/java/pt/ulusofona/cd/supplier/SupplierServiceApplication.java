package pt.ulusofona.cd.supplier;
import org.springframework.boot.SpringApplication; import org.springframework.boot.autoconfigure.SpringBootApplication; import org.springframework.boot.context.properties.EnableConfigurationProperties; import pt.ulusofona.cd.supplier.config.ServiceUrls;
@SpringBootApplication @EnableConfigurationProperties(ServiceUrls.class) public class SupplierServiceApplication {public static void main(String[] args){SpringApplication.run(SupplierServiceApplication.class,args);}}
