package dev.yafatek.restcore.networking.api;


import android.graphics.Bitmap;

import java.io.File;
import java.lang.reflect.Type;

/**
 * interface to Hold the Core Functions of the backend api call methods
 *
 * @author Feras E Alawadi
 * @version 1.0.0
 * @since 1.0.107
 */
public interface NetworkClient {

    /**
     * Get data from apis  without Auth headers.
     *
     * @param <T>         return type
     * @param type        the response class type
     * @param withHeaders to bind the token
     * @return response based on the type param
     */
    <T> T get(Type type, boolean withHeaders);

    /**
     * Method to Upload File to the APIs
     *
     * @param type the return type
     * @param file the file to be uploaded
     * @param <T>  the Type of the response
     * @return apiResponse from the APIs
     */
    <T> T uploadFile(Type type, File file);

    /**
     * method to perform POST request to the Api
     *
     * @param <T>         the Object type
     * @param <R>         the return type
     * @param type        the type of the Object
     * @param body        the json Object to send to the api
     * @param withHeaders to bind the token
     * @return an instance to continue chaining
     */
    <T, R> R post(Type type, T body, boolean withHeaders);


    /**
     * method to load Image as bitmap from the APIs
     *
     * @param imgUrl the image path
     * @return Bitmap object
     */
    Bitmap loadImage(String imgUrl);

    /**
     * load images in async flavor in android systems.
     *
     * @param imgUrl the image full url
     * @return bitmap represents an image
     */
    Bitmap loadAsyncImage(String imgUrl);

    /**
     * method to Perform Delete request to the Api.
     *
     * @param type type of the object
     * @param <T>  the object type
     * @return an instance to continue chaining
     */
    <T> T delete(Type type);

    /**
     * method to update resource in the DB, send it to the Api to update it
     *
     * @param <T>  the object type
     * @param <R>  the return type
     * @param type the object type
     * @param body the object as json
     * @return an instance to continue chaining
     */
    <T, R> R update(Type type, T body);

    /**
     * Method to perform async update (put, patch) request toward apis
     *
     * @param type the response type
     * @param body the update body
     * @param <T>  the type of the request body
     * @param <R>  the return type
     * @return response type.
     */
    <T, R> R updateAsync(Type type, T body);

    /**
     * Post an Async Request in Flavor of Async Calls
     *
     * @param type the response  type
     * @param body request body as POJO
     * @param <T>  Pojo Type
     * @param <R>  return Type
     * @return Response
     */
    <T, R> R postAsync(Type type, T body);


}