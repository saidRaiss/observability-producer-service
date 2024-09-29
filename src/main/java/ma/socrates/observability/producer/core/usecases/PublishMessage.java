package ma.socrates.observability.producer.core.usecases;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.socrates.observability.producer.config.KafkaConfig;
import ma.socrates.observability.producer.core.model.Message;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class PublishMessage {

    private final KafkaConfig kafkaConfig;
    private final KafkaTemplate<String, Message> kafkaTemplate;

    public String handle(Message message) {
        log.trace("Trace details: producer try publish new message: {}", message);
        var future = kafkaTemplate.send(kafkaConfig.getTopic(), message);
        future.whenComplete((sendResult, exception) -> {
            if (exception != null) {
                future.completeExceptionally(exception);
            } else {
                future.complete(sendResult);
            }
            log.debug("Debug details: Task status send to Kafka topic : {}, Message: {}", kafkaConfig.getTopic(), message);
        });
        log.info("Info details: producer published new message: {}", message);
        return "Done";
    }

}
