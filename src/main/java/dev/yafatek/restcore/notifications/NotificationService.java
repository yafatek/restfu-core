package dev.yafatek.restcore.notifications;

/**
 * Service interface to publish an event happened in the system to the Notifications microservice
 * then that microservice will take care of publishing the notification to FireBase.
 *
 * @author Feras E Alawadi
 * @version 1.0.101
 * @since 1.0.101
 */
public interface NotificationService {

    /**
     * Method to Publish an event to Notifications MicroService
     *
     * @param payload      Message PayLoad
     * @param exchangeName the notifications MicroService Exchange name
     * @param routingKey   topic
     */
    void publishNotification(NotificationMessage payload, String exchangeName, String routingKey);

}
