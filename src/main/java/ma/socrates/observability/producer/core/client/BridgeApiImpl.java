package ma.socrates.observability.producer.core.client;

import ma.socrates.observability.producer.api.BridgeApi;
import ma.socrates.observability.producer.core.model.Message;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

public class BridgeApiImpl implements BridgeApi {

    private final String baseUrl;
    private final RestClient restClient;

    public BridgeApiImpl(String baseUrl) {
        this.baseUrl = baseUrl;
        this.restClient = RestClient.builder()
                .requestFactory(new HttpComponentsClientHttpRequestFactory())
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public Message getMessage(String id) {
        return restClient.get()
                .uri(baseUrl, id)
                .retrieve()
                .body(Message.class);
    }
}
