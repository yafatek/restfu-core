package dev.yafatek.restcore.networking.wrappers;


import dev.yafatek.restcore.networking.api.NetworkClient;
import dev.yafatek.restcore.networking.api.NetworkClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * wrapper on top of the library builder interface,
 * is used to bind the request params
 */
public final class NetworkClientBuilderWrapper implements NetworkClientBuilder {

    /**
     * singleton instance
     */
    private static volatile NetworkClientBuilderWrapper networkClientBuilderWrapper;
    /**
     * hashtable to hold any extra stuff the api needs.
     **/
    private final Map<String, String> extras = new HashMap<>();
    /**
     * class level logger
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(NetworkClientBuilderWrapper.class.getName());

    /**
     * singleton only.
     **/
    private NetworkClientBuilderWrapper() {
    }

    /**
     * to get singleton instance
     *
     * @return this object
     */
    public static NetworkClientBuilderWrapper getInstance() {
        LOGGER.info(" [.] NetworkClientBuilderWrapper instance at: {} ", Instant.now());
        if (networkClientBuilderWrapper == null) {
            synchronized (NetworkClientBuilderWrapper.class) {
                networkClientBuilderWrapper = new NetworkClientBuilderWrapper();
            }
        }
        return networkClientBuilderWrapper;
    }

    /**
     * to bind the url
     *
     * @param apiUrl the End point url
     * @return this class for chaining
     */
    @Override
    public NetworkClientBuilder url(String apiUrl) {
        extras.put("url", apiUrl);
        return this;
    }

    /**
     * to build the headers
     *
     * @param headers hashtable contains the params
     * @return this class for chaining
     */
    @Override
    public NetworkClientBuilder headers(Map<String, String> headers) {
        // adding the headers.
        extras.putAll(headers);
        return this;
    }

    /**
     * used to navigate to @NetworkClient impl
     *
     * @return NetworkClient instance.
     */
    @Override
    public NetworkClient then() {
        return BasicNetworkClientWrapper.getInstance(extras);
    }

}
