package com.afalqubaily.fakeinvestment.config;

import com.afalqubaily.fakeinvestment.models.Quote;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    /**
     * This method is for creating the kafka producer configuration.
     * @return an props map.
     */
    @Bean
    public Map<String, Object> getKafkaProducerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, CustomObjectSerializer.class);
        return props;
    }

    /**
     * This method is for creating producer instances.
     * @return DefaultKafkaProducer.
     */
    @Bean
    public ProducerFactory<String, Quote> getProducerFactory() {
        return new DefaultKafkaProducerFactory<>(getKafkaProducerConfig());
    }

    /**
     * This method is responsible for creating events/messages.
     * @param getProducerFactory dependency injection.
     * @return KafkaTemplate Object.
     */
    @Bean
    public KafkaTemplate<String, Quote> getKafkaTemplate(
            ProducerFactory<String, Quote> getProducerFactory
    ) {
        return new KafkaTemplate<>(getProducerFactory);
    }


    /**
     * This class implements the Serializer from Kafka,
     * so we can override its method and turn our object (quote) to json.
     */
    public static class CustomObjectSerializer implements Serializer<Quote> {
        ObjectMapper objectMapper = JsonMapper.builder() // or different mapper for other format
                .addModule(new ParameterNamesModule())
                .addModule(new Jdk8Module())
                .addModule(new JavaTimeModule())
                // and possibly other configuration, modules, then:
                .build();

        @Override
        public byte[] serialize(String s, Quote quote) {
            try {
                return objectMapper.writeValueAsBytes(quote);
            } catch (Exception e) {
                return (e.getMessage()).getBytes();
            }
        }
    }
}
