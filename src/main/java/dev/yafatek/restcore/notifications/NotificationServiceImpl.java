package dev.yafatek.restcore.notifications;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

/**
 * Spring Based Managed Class to process and send notifications to rabbitMQ then to Notifications Service.
 *
 * @author Feras E Alawadi
 * @version 1.0.101
 * @since 1.0.101
 */
@Component
public class NotificationServiceImpl implements NotificationService {
    /**
     * default class logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationServiceImpl.class);
    /**
     * rabbitMQ template.
     */
    private final AsyncRabbitTemplate asyncRabbitTemplate;

    /**
     * constructor to autowire the rabbitMQ template.
     *
     * @param asyncRabbitTemplate the rabbit template.
     */
    public NotificationServiceImpl(AsyncRabbitTemplate asyncRabbitTemplate) {
        this.asyncRabbitTemplate = asyncRabbitTemplate;
    }

    /**
     * Method to push notification
     *
     * @param payload      Message PayLoad
     * @param exchangeName the notifications MicroService Exchange name
     * @param routingKey   topic
     */
    @Override
    public void publishNotification(NotificationMessage payload, String exchangeName, String routingKey) {

        LOGGER.info("publish notification to: {}", payload.getDestination());
        ListenableFuture<String> listenableFuture =
                asyncRabbitTemplate.convertSendAndReceiveAsType(
                        exchangeName,
                        routingKey,
                        payload,
                        new ParameterizedTypeReference<>() {
                        });

        try {
            listenableFuture.get();
            LOGGER.info("Notification message sent");
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.error("Cannot get response.", e);
        }

    }

}
