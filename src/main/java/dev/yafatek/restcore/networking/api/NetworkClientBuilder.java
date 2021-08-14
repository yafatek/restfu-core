package dev.yafatek.restcore.networking.api;


import dev.yafatek.restcore.networking.wrappers.NetworkClientBuilderWrapper;

import java.util.Map;

/**
 * a network builder used as a starting point to init the library usage.
 * used to init the urls, params. and then redirect to perform network request/
 *
 * @author Feras E. Alawadi
 * @version 1.0.101
 * @since 1.0.107
 */
public interface NetworkClientBuilder {
    /**
     * method to Build a new implementation of the interface
     *
     * @param clazz the class
     * @param <E>   return type usually the class that implements the interface
     * @return new instance of the new implementation
     * @throws Exception Constructors Exception
     */
    static <E> E build(Class<E> clazz) throws Exception {
        return clazz.getDeclaredConstructor().newInstance();
    }

    /**
     * method to build the object with the default interface implementation.
     * ded
     *
     * @return new instance of that implementation.
     */
    static NetworkClientBuilderWrapper defaults() {
        return NetworkClientBuilderWrapper.getInstance();
    }

    /**
     * Method to get the api url (usually the End point for a resource)
     *
     * @param apiUrl the End point url
     * @return chaining Object
     */
    NetworkClientBuilder url(String apiUrl);

    /**
     * request headers and parameters such as URL, token, refreshToken ...etc
     *
     * @param headers hashtable contains the params
     * @return instance
     */
    NetworkClientBuilder headers(Map<String, String> headers);

    /**
     * method to navigate to the reset of the operations
     *
     * @return network client object
     */
    NetworkClient then();
}
