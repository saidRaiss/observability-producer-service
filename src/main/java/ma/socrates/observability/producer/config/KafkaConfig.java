package ma.socrates.observability.producer.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class KafkaConfig {

    private String bootstrap;
    private String topic;
}

