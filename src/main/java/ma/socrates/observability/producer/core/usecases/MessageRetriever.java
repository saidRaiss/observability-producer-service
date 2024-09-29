package ma.socrates.observability.producer.core.usecases;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.socrates.observability.producer.api.BridgeApi;
import ma.socrates.observability.producer.core.model.Message;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class MessageRetriever {

    private final BridgeApi bridgeApi;

    public Message handle(String id) {
        log.info("Retrieve message id: {}", id);
        return bridgeApi.getMessage(id);
    }
}
