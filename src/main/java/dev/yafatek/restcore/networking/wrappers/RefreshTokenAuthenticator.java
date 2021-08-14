package dev.yafatek.restcore.networking.wrappers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dev.yafatek.restcore.networking.models.RefreshTokenRequest;
import dev.yafatek.restcore.networking.models.RefreshTokenResponse;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


/**
 * Authenticator class that is used to Check either we have a Valid Access token or its Expired :)\
 * refresh the access token in case we have an expired one :)
 *
 * @author Feras E Alawadi
 * @version 1.0.101
 * @since 1.0.107
 */
public class RefreshTokenAuthenticator implements Authenticator {
    /**
     * class level logger
     */
    private static final Logger LOGGER = Logger.getLogger(RefreshTokenAuthenticator.class.getName());
    /**
     * request media type
     */
    private static final MediaType JSON_UTF8 = MediaType.parse("application/json; charset=utf-8");
    /**
     * refresh token response pojo
     */
    private static RefreshTokenResponse responseApiResponse;
    /**
     * app context in flavor of Android Systems.
     */
    private final Context context;
    /**
     * APIs URL
     */
    private final String url;

    /**
     * pass the context and the api url
     *
     * @param context system ctx
     * @param url     Apis url
     */
    public RefreshTokenAuthenticator(Context context, String url) {
        this.context = context;
        this.url = url;
    }

    /**
     * to perform init authenticate with the apis
     *
     * @param route    the dest
     * @param response the api response to determine if we have an expired token
     * @return the request
     */
    @Override
    public Request authenticate(Route route, @NotNull Response response) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        String accessToken = prefs.getString("refreshToken", "");
        if (!isRequestWithAccessToken(response) || accessToken == null) {
            return null;
        }
        LOGGER.info(" [.] Starting Authenticator...");
        synchronized (this) {
            final String oldRefreshToken = prefs.getString("refreshToken", "");
            // previous Call needs a refresh token
            final String newAccessToken = prefs.getString("token", "");
            if (accessToken.equals(newAccessToken) && response.code() != 401) {
                // token refreshed prev.
                LOGGER.info(" [*] rollback to request, Valid Token Exist");
                return newRequestWithAccessToken(response.request(), newAccessToken);
            } else {
                // refresh token.
                String newToken = refreshToken(oldRefreshToken, prefs, url);
                LOGGER.info(" [*] Refreshing AccessToken: " + newToken);
                return newRequestWithAccessToken(response.request(), newToken);
            }
        }
    }


    /**
     * check if there is a need to check and refresh the token
     *
     * @param response the current response
     * @return true, false
     */
    private boolean isRequestWithAccessToken(Response response) {
        String header = response.request().header("Authorization");
        return header != null && header.startsWith("Bearer");
    }

    /**
     * method to refresh it
     *
     * @param oldRefreshToken the old one
     * @param prefs           get it out of the perf in android
     * @param url             the api url to refresh the token
     * @return refreshed token
     */
    private String refreshToken(String oldRefreshToken, SharedPreferences prefs, String url) {
        final OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        final Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(JSON_UTF8, gson.toJson(new RefreshTokenRequest(oldRefreshToken)));
        Request request = new Request.Builder()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .url(url)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LOGGER.info(" [x] Failed When Obtaining Refresh Token...");
                // remove the token from the system in case backend failed to refresh the token...
                SharedPreferences globalPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = globalPreferences.edit();
                // remove the token
                editor.remove("token").apply();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                responseApiResponse = new Gson().fromJson(Objects.requireNonNull(response.body()).charStream(), new TypeToken<RefreshTokenResponse>() {
                }.getType());
                if (responseApiResponse.getToken() == null) {
                    SharedPreferences globalPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                    @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = globalPreferences.edit();
                    // remove the token
                    editor.remove("token").apply();
                } else
                    LOGGER.info(" [.] new token obtained: " + responseApiResponse.getToken());
            }
        });
        if (responseApiResponse != null) {
            LOGGER.info(" [.] return new token: " + responseApiResponse.getToken());
            @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = prefs.edit();
            editor.putString("token", responseApiResponse.getToken()).apply();
            editor.putString("refreshToken", responseApiResponse.getRefreshToken()).apply();
            return responseApiResponse.getToken();
        }
        return null;
    }


    /**
     * bind the new or the old token and chain it with the request.
     *
     * @param request     the request
     * @param accessToken the token
     * @return okHttp request
     */
    private Request newRequestWithAccessToken(Request request, String accessToken) {
        return request.newBuilder()
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();
    }
}
