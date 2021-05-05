package dev.yafatek.restcore.api.utils;

import dev.yafatek.restcore.api.enums.ApiResponseCodes;
import dev.yafatek.restcore.api.responses.ApiResponse;
import dev.yafatek.restcore.api.responses.ErrorResponse;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.UUID;

/**
 * Helper methods to use generic Api responses with and without errors.
 *
 * @author Feras E Alawadi
 * @version 1.0.1
 * @since 1.0.1
 */
public final class ApiUtils {

    /**
     * method to return success response
     *
     * @param status  boolean
     * @param message string
     * @param code    enum
     * @param body    the response body
     * @param error   error code
     * @param <T>     type
     * @param <R>     error type
     * @return api response
     */
    public static <T, R> ApiResponse<T, R> successResponse(boolean status, String message, String code, T body, R error) {
        return new ApiResponse<>(
                status,
                code,
                message,
                body,
                error
        );
    }

    /**
     * return suceess response
     *
     * @param status  boolean
     * @param message String
     * @param code    enum
     * @param body    the body type
     * @param <T>     type
     * @return api response
     */
    public static <T> ApiResponse<T, ErrorResponse> success(boolean status, String message, String code, T body) {
        return new ApiResponse<>(
                status,
                code,
                message,
                body
        );
    }

    /**
     * Method to send success response but with error body as well.
     *
     * @param status  status code true or false
     * @param message message content
     * @param code    response code
     * @param body    response body usually a Java POJO Class
     * @param errors  Error POJO
     * @param <T>     Type of the POJO
     * @param <R>     Error POJO
     * @return T body and R error types
     * @see ApiResponseCodes
     * @see ErrorResponse
     */
    public static <T, R> ApiResponse<T, R> successWithErrorsResponse(boolean status, String message, String code, T body, R errors) {
        return new ApiResponse<>(
                status,
                code,
                message,
                body,
                errors
        );
    }

    /**
     * Method to return Error response
     *
     * @param status  usually false status
     * @param message message content
     * @param code    api code
     * @param <T>     body type
     * @param <R>     error type
     * @return T for results and R for errors
     * @see ApiResponseCodes
     * @see ErrorResponse
     */
    public static <T, R> ApiResponse<T, R> errorResponse(boolean status, String message, String code) {
        return new ApiResponse<>(
                status,
                code,
                message,
                null,
                null
        );
    }

    /**
     * returns an api response with error body
     *
     * @param status  true or false
     * @param message error message
     * @param code    error code
     * @param error   error pojo
     * @param <T>     null
     * @param <R>     error pojo type
     * @return response
     */
    public static <T, R> ApiResponse<T, R> errorResponse(boolean status, String message, String code, R error) {
        return new ApiResponse<>(
                status,
                code,
                message,
                null,
                error
        );
    }

    /**
     * Method to validate UUID Strings format
     *
     * @param uuid string
     * @return bollean response
     */
    public static boolean validUUID(String uuid) {
        try {
            UUID.fromString(uuid);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * method that takes a string and return a Instant Object.
     *
     * @param offset the date as string
     * @return Instant object.
     */
    public static Instant stringToInstant(String offset) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        TemporalAccessor temporalAccessor = formatter.parse(offset);
        LocalDateTime localDateTime = LocalDateTime.from(temporalAccessor);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.of("UTC"));
        return Instant.from(zonedDateTime);
    }
}
