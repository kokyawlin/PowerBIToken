package com.powerbi.token.model;

public class EmbedConfig {
    
    private String embedToken;
    private String embedUrl;
    private String reportId;
    private String tokenId;
    private String expiration;

    public EmbedConfig() {
    }

    public EmbedConfig(String embedToken, String embedUrl, String reportId, String tokenId, String expiration) {
        this.embedToken = embedToken;
        this.embedUrl = embedUrl;
        this.reportId = reportId;
        this.tokenId = tokenId;
        this.expiration = expiration;
    }

    public String getEmbedToken() {
        return embedToken;
    }

    public void setEmbedToken(String embedToken) {
        this.embedToken = embedToken;
    }

    public String getEmbedUrl() {
        return embedUrl;
    }

    public void setEmbedUrl(String embedUrl) {
        this.embedUrl = embedUrl;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
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
