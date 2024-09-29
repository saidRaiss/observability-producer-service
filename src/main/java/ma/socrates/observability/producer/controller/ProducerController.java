package ma.socrates.observability.producer.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.socrates.observability.producer.core.model.Message;
import ma.socrates.observability.producer.core.usecases.MessageRetriever;
import ma.socrates.observability.producer.core.usecases.PublishMessage;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@AllArgsConstructor
public class ProducerController {

    private final PublishMessage publishMessage;
    private final MessageRetriever messageRetriever;

    @PostMapping("/producer/publish")
    public String publishMessage(@RequestBody Message message) {
        return publishMessage.handle(message);
    }

    @GetMapping("/message")
    public Message getMessageById(@RequestParam String id) {
        return messageRetriever.handle(id);
    }


}

