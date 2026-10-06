package pt.ulusofona.cd.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.client.support.RestClientAdapter;
import pt.ulusofona.cd.product.client.SupplierClient;

import java.net.http.HttpClient;
import java.time.Duration;

@Configuration
public class HttpClientConfig {
    @Bean
    SupplierClient supplierClient(ServiceUrls urls) {
        HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(2)).build();
        JdkClientHttpRequestFactory factory = new JdkClientHttpRequestFactory(httpClient);
        factory.setReadTimeout(Duration.ofSeconds(3));
        RestClient restClient = RestClient.builder().baseUrl(urls.supplierBaseUrl()).requestFactory(factory).build();
        HttpServiceProxyFactory proxy = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
        return proxy.createClient(SupplierClient.class);
    }
}
