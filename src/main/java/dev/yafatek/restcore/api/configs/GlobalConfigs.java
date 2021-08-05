package dev.yafatek.restcore.api.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * Global System Configurations
 *
 * @author feeras E. Alawadi
 * @version 1.0.101
 * @since 1.0.102
 */
@Configuration
public class GlobalConfigs {

    /**
     * JSON Converter
     *
     * @return Jackson JSON Converter
     */
    @Bean
    public MessageConverter jackson2MessageConverter() {
        return new Jackson2JsonMessageConverter(objectMapper(new Jackson2ObjectMapperBuilder()));
    }

    /**
     * Object Mapper JSON Converter
     *
     * @param builder jackson object
     * @return Mapper
     */
    @Bean
    @Primary
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.build();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return objectMapper;
    }

    /**
     * RABBITMQ Template.
     *
     * @param rabbitTemplate rabbit Template
     * @return Async Rabbit Template
     */
    @Bean
    public AsyncRabbitTemplate asyncRabbitTemplate(RabbitTemplate rabbitTemplate) {
        return new AsyncRabbitTemplate(rabbitTemplate);
    }
}
