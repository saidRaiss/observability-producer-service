package ma.socrates.observability.producer;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Slf4j
public class KafkaProducer {

    private static final String TOPIC = "topic-observability";

    private final KafkaTemplate<String, Message> kafkaTemplate;


    @PostMapping("/publish")
    public String publishMessage(@RequestBody Message message) {
        var future = kafkaTemplate.send(TOPIC, message);
        future.whenComplete((sendResult, exception) -> {
            if (exception != null) {
                future.completeExceptionally(exception);
            } else {
                future.complete(sendResult);
            }
            log.info("Task status send to Kafka topic : {}, Message: {}", TOPIC, message);
        });
        return "Done";
    }

}

