package ma.socrates.observability.producer.api;

import ma.socrates.observability.producer.core.model.Message;

public interface BridgeApi {

    Message getMessage(String id);

}
