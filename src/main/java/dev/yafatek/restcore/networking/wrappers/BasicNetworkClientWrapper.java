package dev.yafatek.restcore.networking.wrappers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.google.gson.Gson;
import dev.yafatek.restcore.networking.api.NetworkClient;
import dev.yafatek.restcore.networking.callbacks.CallbackFuture;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Wrapper class on top of the OkHttp Library to perform common APIs Operations
 * such as, GET, POST ...etc
 * for deserializing the objects please refer to: https://stackoverflow.com/questions/34660339/gson-deserialization-with-generic-types-and-generic-field-names
 *
 * @author Feras E Alawadi
 * @version 1.0.101
 * @since 1.0.107
 */
public final class BasicNetworkClientWrapper implements NetworkClient {
    /**
     * class Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BasicNetworkClientWrapper.class.getName());
    /**
     * metadata for the post request.
     **/
    private static final MediaType JSON_UTF8 = MediaType.parse("application/json; charset=utf-8");
    /**
     * External Api Client
     **/
    private static final OkHttpClient client = new OkHttpClient.Builder()
            // refresh token authenticator is used to refresh accessTokens When they are expired
//            .authenticator(new RefreshTokenAuthenticator())
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build();
    /**
     * Gson Converter Global Object.
     */
    private final Gson gson = new Gson();
    /**
     * this class singleton object.
     */
    private static volatile BasicNetworkClientWrapper basicNetworkClientWrapper;
    /**
     * extra api attributes
     **/
    private static final Map<String, String> extras = new HashMap<>();

    /**
     * SingleTon Object only
     **/
    private BasicNetworkClientWrapper() {
    }

    /**
     * singleton object instance
     *
     * @param attributes hastable of the request attr like url, headers ...etc.
     * @return instance
     */
    /* singleton object instance */
    public static BasicNetworkClientWrapper getInstance(Map<String, String> attributes) {
        extras.putAll(attributes);
        // the object is expensive so use only the singleton object.
        if (basicNetworkClientWrapper == null) {
            synchronized (BasicNetworkClientWrapper.class) {
                return basicNetworkClientWrapper = new BasicNetworkClientWrapper();
            }
        }
        return basicNetworkClientWrapper;
    }

    /**
     * Method to Upload File to the APIs
     *
     * @param type the return type
     * @param file the file to be uploaded
     * @param <T>  the Type of the response
     * @return apiResponse from the APIs
     */
    @Override
    public <T> T uploadFile(Type type, File file) {
        RequestBody formBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(),
                        RequestBody.create(MediaType.parse("multipart/form-data"), file))
                .build();
        Request request = new Request.Builder()
                .url(Objects.requireNonNull(extras.get("url")))
                .post(formBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return gson.fromJson(Objects.requireNonNull(response.body()).charStream(), type);
            } else {
                LOGGER.info("Can't Upload File: code: " + response.code());
                return null;
            }

        } catch (IOException e) {
            LOGGER.info("can't Upload the File, check the api call");
            return null;
        }
    }

    /**
     * * method to build the request headers
     * based on the requirements of the call
     *
     * @param url         the api url
     * @param token       request token
     * @param withHeaders true or false to bind the token
     * @return okHttp Request Object
     */
    private Request buildRequest(String url, String token, boolean withHeaders) {
        if (withHeaders)
            return new Request.Builder()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + token)
                    .url(url)
                    .build();

        else
            return new Request.Builder()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .url(url)
                    .build();
    }

    /**
     * method to build the request headers
     * based on the requirements of the call
     *
     * @param url         the url
     * @param token       the token if required
     * @param requestBody the request body
     * @param withHeaders true or false to build the required headers
     * @return okhttp Request
     */
    private Request buildRequest(String url, String token, RequestBody requestBody, boolean withHeaders) {
        if (withHeaders)
            return new Request.Builder()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + token)
                    .url(url)
                    .post(requestBody)
                    .build();
        else
            return new Request.Builder()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .url(Objects.requireNonNull(extras.get("url")))
                    .post(requestBody)
                    .build();
    }

    /**
     * Get data from apis  without Auth headers.
     *
     * @param <T>  return type
     * @param type the response class type
     * @return response based on the type param
     */
    @Override
    public <T> T get(Type type, boolean withHeaders) {
        Request request = buildRequest(extras.get("url"), extras.get("token"), withHeaders);
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return gson.fromJson(Objects.requireNonNull(response.body()).charStream(), type);
            } else {
                LOGGER.info(" [x] can't get response, response code: " + response.code());
                return null;
            }
        } catch (Exception e) {
            LOGGER.info(" [x] can't get response, failure at: " + e.getMessage());
            return null;
        }

    }


    /**
     * method to perform POST request to the Api
     *
     * @param <T>  the Object type
     * @param type the type of the Object
     * @param body the json Object to send to the api
     * @return an instance to continue chaining
     */
    @Override
    public <T, R> R post(Type type, T body, boolean withHeaders) {
        // Generating request body.
        RequestBody requestBody = RequestBody.create(JSON_UTF8, gson.toJson(body));

        Request request = buildRequest(extras.get("url"), extras.get("token"), requestBody, withHeaders);

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return gson.fromJson(Objects.requireNonNull(response.body()).charStream(), type);
            } else {
                LOGGER.info(" [x] can't Post Data To The APIs, response code: " + response.code());
                return null;
            }
        } catch (Exception e) {
            LOGGER.info(" [x] can't post date to the Api, error: " + e.getMessage());
            return null;
        }
    }

    /**
     * method to load Image as bitmap from the APIs
     *
     * @param imgUrl the image path
     * @return Bitmap object
     */
    @Override
    public Bitmap loadImage(String imgUrl) {

        Request request = new Request.Builder()
                .url(imgUrl)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return BitmapFactory.decodeStream(Objects.requireNonNull(response.body()).byteStream());
            } else return null;
        } catch (IOException e) {
            return null;
        }

    }


    /**
     * load images in async flavor in android systems.
     *
     * @param imgUrl the image full url
     * @return bitmap represents an image
     */
    @Override
    public Bitmap loadAsyncImage(String imgUrl) {
        final CallbackFuture future = new CallbackFuture();
        Request request = new Request.Builder()
                .url(imgUrl)
                .build();

        client.newCall(request).enqueue(future);
        try {
            Response response = future.get();
            if (response.isSuccessful()) {
                return BitmapFactory.decodeStream(Objects.requireNonNull(response.body()).byteStream());
            } else return null;
        } catch (ExecutionException | InterruptedException e) {
            return null;
        }
    }

    /**
     * method to Perform Delete request to the Api.
     *
     * @param type type of the object
     * @param <T>  the object type
     * @return an instance to continue chaining
     */
    @Override
    public <T> T delete(Type type) {
        Request request =
                new Request.Builder()
                        .header("Accept", "application/json")
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + Objects.requireNonNull(extras.get("token").trim()))
                        .url(Objects.requireNonNull(extras.get("url").trim()))
                        .delete()
                        .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return gson.fromJson(Objects.requireNonNull(response.body()).string(), type);
            } else {
                LOGGER.info(" [x] can't Delete resource: error code: " + response.code());
                return null;
            }
        } catch (IOException e) {
            LOGGER.info(" [x] can't perform an delete request");
            return null;
        }
    }

    /**
     * method to update resource in the DB, send it to the Api to update it
     *
     * @param <T>  the object type
     * @param type the object type
     * @param body the object as json
     * @return an instance to continue chaining
     */
    @Override
    public <T, R> R update(Type type, T body) {

        RequestBody requestBody = RequestBody.create(JSON_UTF8, gson.toJson(body));
        Request request = new Request.Builder()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + Objects.requireNonNull(extras.get("token").trim()))
                .url(Objects.requireNonNull(extras.get("url").trim()))
                .patch(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {

            if (response.isSuccessful()) {
                return gson.fromJson(response.body() != null ? Objects.requireNonNull(response.body()).string() : null, type);
            } else {
                LOGGER.info(" [x] can't Update resource: error code: " + response.code());
                return null;
            }
        } catch (IOException e) {
            LOGGER.info(" [x] can't perform an update request");
            return null;
        }
    }

    /**
     * Method to perform async update (put, patch) request toward apis
     *
     * @param type the response type
     * @param body the update body
     * @param <T>  the type of the request body
     * @param <R>  the return type
     * @return response type.
     */
    public <T, R> R updateAsync(Type type, T body) {
        final CallbackFuture future = new CallbackFuture();
        RequestBody requestBody = RequestBody.create(JSON_UTF8, gson.toJson(body));
        Request request = new Request.Builder()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + Objects.requireNonNull(extras.get("token")).trim())
                .url(Objects.requireNonNull(extras.get("url")).trim())
                .patch(requestBody)
                .build();

        client.newCall(request).enqueue(future);
        try {
            Response response = future.get();
            if (response.isSuccessful()) {
                return gson.fromJson(response.body() != null ? Objects.requireNonNull(response.body()).string() : null, type);
            } else
                return null;
        } catch (IOException | ExecutionException | InterruptedException ignore) {
            return null;
        }

    }

    /**
     * Post an Async Request in Flavor of Async Calls
     *
     * @param type the response  type
     * @param body request body as POJO
     * @param <T>  Pojo Type
     * @param <R>  return Type
     * @return Response
     */
    public <T, R> R postAsync(Type type, T body) {

        final CallbackFuture future = new CallbackFuture();
        RequestBody requestBody = RequestBody.create(JSON_UTF8, new Gson().toJson(body));

        Request request = new Request.Builder()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + Objects.requireNonNull(extras.get("token")).trim())
                .url(Objects.requireNonNull(extras.get("url")).trim())
                .post(requestBody)
                .build();


        client.newCall(request).enqueue(future);

        try {
            Response response = future.get();
            if (response.isSuccessful()) {
                return gson.fromJson(Objects.requireNonNull(response.body()).charStream(), type);
            } else {
                return null;
            }

        } catch (InterruptedException | ExecutionException e) {
            LOGGER.info(" [x] post exception, failure at: {} ", e.getMessage());
            return null;
        }

    }

}

