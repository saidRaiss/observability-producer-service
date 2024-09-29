package ma.socrates.observability.producer;

import ma.socrates.observability.producer.api.BridgeApi;
import ma.socrates.observability.producer.config.KafkaConfig;
import ma.socrates.observability.producer.config.RestClientConfig;
import ma.socrates.observability.producer.core.client.BridgeApiImpl;
import ma.socrates.observability.producer.core.model.Message;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProducerBeansFactory {

    @Bean
    @ConfigurationProperties(prefix = "app.outbound")
    public RestClientConfig createRestClientConfig() {
        return new RestClientConfig();
    }

    @Bean
    @ConfigurationProperties(prefix = "app.kafka")
    public KafkaConfig createKafkaConfig() {
        return new KafkaConfig();
    }

    @Bean
    public BridgeApi createConsumerApi(@Autowired RestClientConfig restClientConfig) {
        return new BridgeApiImpl(restClientConfig.getBaseUrlOf("bridge"));
    }

    @Bean
    public NewTopic taskTopic(KafkaConfig kafkaConfig) {
        return TopicBuilder.name(kafkaConfig.getTopic())
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public ProducerFactory<String, Message> producerFactory(KafkaConfig kafkaConfig) {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootstrap());
        config.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, Message> kafkaTemplate(ProducerFactory<String, Message> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

}
