package dev.yafatek.restcore.networking.wrappers;


import dev.yafatek.restcore.networking.api.NetworkClient;
import dev.yafatek.restcore.networking.api.NetworkClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public final class NetworkClientBuilderWrapper implements NetworkClientBuilder {

    private static volatile NetworkClientBuilderWrapper networkClientBuilderWrapper;
    // hashtable to hold any extra stuff the api needs.
    private final Map<String, String> extras = new HashMap<>();
    private final static Logger LOGGER = LoggerFactory.getLogger(NetworkClientBuilderWrapper.class.getName());

    // singleton only.
    private NetworkClientBuilderWrapper() {
    }

    public static NetworkClientBuilderWrapper getInstance() {
        LOGGER.info(" [.] NetworkClientBuilderWrapper instance at: {} ", Instant.now());
        if (networkClientBuilderWrapper == null) {
            synchronized (NetworkClientBuilderWrapper.class) {
                networkClientBuilderWrapper = new NetworkClientBuilderWrapper();
            }
        }
        return networkClientBuilderWrapper;
    }

    @Override
    public NetworkClientBuilder url(String apiUrl) {
        extras.put("url", apiUrl);
        return this;
    }

    @Override
    public NetworkClientBuilder headers(Map<String, String> headers) {
        // adding the headers.
        extras.putAll(headers);
        return this;
    }

    @Override
    public NetworkClient then() {
        return BasicNetworkClientWrapper.getInstance(extras);
    }

}
