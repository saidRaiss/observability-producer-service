package ma.socrates.observability.producer;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Slf4j
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "topic-observability";

    @PostMapping("/publish")
    public String publishMessage(@RequestBody Message message) {
        kafkaTemplate.send(TOPIC, message.content());
        log.info("Message published successfully");
        return "Done";
    }

}

