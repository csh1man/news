package com.krinvest.news.evest.dto;

import com.google.gson.annotations.SerializedName;

public class TokenInfo {
    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("scope")
    private String oob;
    @SerializedName("token_type")
    private String tokenType;
    @SerializedName("expires_in")
    private int expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getOob() {
        return oob;
    }

    public void setOob(String oob) {
        this.oob = oob;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
}
