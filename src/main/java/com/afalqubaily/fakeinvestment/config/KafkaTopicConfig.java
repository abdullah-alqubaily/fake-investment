package com.afalqubaily.fakeinvestment.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    /**
     * This method is used to create a kafka topic.
     */
    @Bean
    public NewTopic quotesTopic() { return TopicBuilder.name("stock-quotes").build(); }
}
