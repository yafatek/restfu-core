package dev.yafatek.restcore.networking.models;

/**
 * refresh token response pojo
 *
 * @author Feras E.Alawadi
 * @version 1.0.101
 * @since 1.0.107
 */
public class RefreshTokenResponse {
    /**
     * new token
     */
    private String token;
    /**
     * refreshed token
     */
    private String refreshToken;

    /**
     * empty one
     */
    public RefreshTokenResponse() {
    }

    /**
     * to construct local params
     *
     * @param token        the new token
     * @param refreshToken the refresh token
     */
    public RefreshTokenResponse(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }

    /**
     * get the token
     *
     * @return token as string
     */
    public String getToken() {
        return token;
    }


    /**
     * get refresh token
     *
     * @return refresh token
     */
    public String getRefreshToken() {
        return refreshToken;
    }


    @Override
    public String toString() {
        return "RefreshTokenResponse{" +
                "token='" + token + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
