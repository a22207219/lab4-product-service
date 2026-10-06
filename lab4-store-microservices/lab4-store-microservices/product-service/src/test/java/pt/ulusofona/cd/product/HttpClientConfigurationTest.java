package pt.ulusofona.cd.product;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class HttpClientConfigurationTest {
    @Test
    void springHttpClientBuildingBlocksAreAvailable() {
        RestClient client = RestClient.builder().baseUrl("http://localhost:8082").build();
        assertNotNull(HttpServiceProxyFactory.builderFor(RestClientAdapter.create(client)).build());
    }
}
