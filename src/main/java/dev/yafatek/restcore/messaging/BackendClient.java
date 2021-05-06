package dev.yafatek.restcore.messaging;


import org.springframework.messaging.handler.annotation.SendTo;

/**
 * High level RabbitMQ Client to send and retrieve Data out of Consumer Services that listens to Specific queues
 * and targeting Topics. Usually this is a mix between RPC(Remote procedure call) and Topic Based Technique.
 *
 * @author Feras E Alawadi
 * @version 1.0.1
 * @since 1.0.3
 */
public interface BackendClient {
    /**
     * Method to Call remote Method on targeted Service based on the DirectExchange and following the RoutingKey to bind the
     * response to the publisher source.
     *
     * @param payload    Pojo Object of Type T
     * @param routingKey specific key to bind the request to a specific queue
     * @param <T>        type of pojo Object usually the request body
     * @param <R>        the return type, its usually represents the response pojo Object
     * @return pojo object represents the service response
     */
    @SendTo
    <T, R> R sendWithAsync(T payload, String routingKey, String exchangeName);

    /**
     * Method To Call Get Services Without Parameters.
     *
     * @param routingKey destination Method at remote Server
     * @param <R>        Return Type, usually the response that the remote service return.
     * @return remote service response type.
     */
    <R> R sendWithAsync(String routingKey, String exchangeName);

}
