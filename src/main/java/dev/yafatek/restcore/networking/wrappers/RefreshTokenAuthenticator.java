package dev.yafatek.restcore.networking.wrappers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dev.yafatek.networking.v1.models.RefreshTokenRequest;
import dev.yafatek.networking.v1.models.RefreshTokenResponse;
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
    private static final Logger LOGGER = Logger.getLogger(RefreshTokenAuthenticator.class.getName());
    private static final MediaType JSON_UTF8 = MediaType.parse("application/json; charset=utf-8");
    private static RefreshTokenResponse responseApiResponse;
    private final Context context;
    private final String url;

    public RefreshTokenAuthenticator(Context context, String url) {
        this.context = context;
        this.url = url;
    }

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


    private boolean isRequestWithAccessToken(Response response) {
        String header = response.request().header("Authorization");
        return header != null && header.startsWith("Bearer");
    }

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


    private Request newRequestWithAccessToken(Request request, String accessToken) {
        return request.newBuilder()
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();
    }
}
