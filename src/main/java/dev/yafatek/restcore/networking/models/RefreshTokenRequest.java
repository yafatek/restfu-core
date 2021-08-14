package dev.yafatek.restcore.networking.models;

/**
 * refresh api token request body
 *
 * @author Feras E. Alawadi
 * @version 1.0.101
 * @since 1.0.107
 */
public final class RefreshTokenRequest {
    /**
     * refresh token
     */
    private String refreshToken;

    /**
     * class constructor
     */
    public RefreshTokenRequest() {
    }

    /**
     * constructor to set refresh token
     *
     * @param refreshToken the new token
     */
    public RefreshTokenRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    /**
     * get the old  token
     *
     * @return a refreshed token
     */
    public String getRefreshToken() {
        return refreshToken;
    }

}
