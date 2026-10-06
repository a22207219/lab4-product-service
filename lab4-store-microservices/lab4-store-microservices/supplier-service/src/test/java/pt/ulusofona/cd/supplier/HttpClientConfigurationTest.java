package pt.ulusofona.cd.supplier;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class HttpClientConfigurationTest {
    @Test
    void springHttpClientBuildingBlocksAreAvailable() {
        RestClient client = RestClient.builder().baseUrl("http://localhost:8081").build();
        assertNotNull(HttpServiceProxyFactory.builderFor(RestClientAdapter.create(client)).build());
    }
}
