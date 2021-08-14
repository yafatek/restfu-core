package dev.yafatek.restcore.networking.callbacks;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;


/**
 * Future callbacks used with okHttp
 *
 * @author Feras E. Alawadi
 * @version 1.0.101
 * @since 1.0.107
 */
public class CallbackFuture extends CompletableFuture<Response> implements Callback {

    /**
     * catch on failure
     *
     * @param call the network call
     * @param e    exception
     */
    @Override
    public void onFailure(@NotNull Call call, @NotNull IOException e) {
        super.completeExceptionally(e);
    }

    /**
     * catch the future response and return it
     *
     * @param call     the network call
     * @param response the api response
     */
    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) {
        super.complete(response);
    }
}
