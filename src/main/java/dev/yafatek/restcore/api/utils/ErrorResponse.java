package dev.yafatek.restcore.api.utils;

/**
 * Error POJO class used to push a generic error response!
 *
 * @author Feras E Alawadi
 * @version 1.0.101
 * @since 1.0.105
 */
public class ErrorResponse {
    private String errorMessage;
    private String errorCode;

    public ErrorResponse() {
    }

    public ErrorResponse(String errorMessage, String errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
