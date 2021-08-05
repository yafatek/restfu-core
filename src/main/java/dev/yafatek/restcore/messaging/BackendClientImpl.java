package dev.yafatek.restcore.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

/**
 * Default Remote Services Client implementation that is used to call a methods on remote microservices.
 *
 * @author Feras E Alawadi
 * @version 1.0.1
 * @since 1.0.3
 */
@Component
public class BackendClientImpl implements BackendClient {
    /**
     * System LOGGER
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BackendClientImpl.class);
    /**
     * ASYNC RabbitMQ Object
     */
    private final AsyncRabbitTemplate asyncRabbitTemplate;

    /**
     * Default RABBITQ Autowiring
     *
     * @param asyncRabbitTemplate RABBITMQ Async TEmplate
     */
    public BackendClientImpl(AsyncRabbitTemplate asyncRabbitTemplate) {
        this.asyncRabbitTemplate = asyncRabbitTemplate;
    }

    /**
     * Method To Send DATA Using RABBITMQ QUEUE
     * used to publish events to target queue with key and exchange
     *
     * @param payload      Pojo Object of Type T the pay lload
     * @param routingKey   specific key to bind the request to a specific queue
     * @param exchangeName target exchange
     * @param <T>          Send Body Type
     * @param <R>          Retrun Type
     * @return the response in json
     */
    @Override
    public <T, R> R sendWithAsync(T payload, String routingKey, String exchangeName) {

        ListenableFuture<R> listenableFuture =
                asyncRabbitTemplate.convertSendAndReceiveAsType(
                        exchangeName,
                        routingKey,
                        payload,
                        new ParameterizedTypeReference<>() {
                        });
        try {
            return listenableFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.error("Cannot get RABBITMQ response.", e);
            return null;
        }
    }

    /**
     * Publish an Event to rabbitMQ Queue Without a Body
     *
     * @param routingKey   destination Method at remote Server
     * @param exchangeName target exchange
     * @param <R>          return Type POJO
     * @return response as Json
     */
    @Override
    public <R> R sendWithAsync(String routingKey, String exchangeName) {
        ListenableFuture<R> listenableFuture =
                asyncRabbitTemplate.convertSendAndReceiveAsType(
                        exchangeName,
                        routingKey,
                        "",
                        new ParameterizedTypeReference<>() {
                        });

        try {
            return listenableFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.info("error calling destination service, exchange: {}", exchangeName);
            return null;
        }

    }
}
