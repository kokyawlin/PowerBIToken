package com.powerbi.token.model;

public class EmbedToken {
    
    private String token;
    private String tokenId;
    private String expiration;

    public EmbedToken() {
    }

    public EmbedToken(String token, String tokenId, String expiration) {
        this.token = token;
        this.tokenId = tokenId;
        this.expiration = expiration;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }
}
