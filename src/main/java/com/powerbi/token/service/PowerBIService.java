package com.powerbi.token.service;

import com.azure.core.credential.AccessToken;
import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.powerbi.token.config.PowerBIProperties;
import com.powerbi.token.model.EmbedConfig;
import com.powerbi.token.model.EmbedToken;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class PowerBIService {

    private final PowerBIProperties properties;
    private final OkHttpClient httpClient;
    private final Gson gson;

    public PowerBIService(PowerBIProperties properties) {
        this.properties = properties;
        this.httpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        this.gson = new Gson();
    }

    /**
     * Get access token using Azure AD authentication
     */
    private String getAccessToken() {
        try {
            ClientSecretCredential credential = new ClientSecretCredentialBuilder()
                    .clientId(properties.getClientId())
                    .clientSecret(properties.getClientSecret())
                    .tenantId(properties.getTenantId())
                    .build();

            AccessToken token = credential.getToken(
                    new com.azure.core.credential.TokenRequestContext()
                            .addScopes(properties.getScope())
            ).block();

            if (token != null) {
                return token.getToken();
            }
            throw new RuntimeException("Failed to acquire access token");
        } catch (Exception e) {
            throw new RuntimeException("Error acquiring access token: " + e.getMessage(), e);
        }
    }

    /**
     * Get embed token for a specific report
     */
    public EmbedConfig getEmbedToken(String workspaceId, String reportId) throws IOException {
        String accessToken = getAccessToken();
        
        // Build the Power BI API URL
        String url = String.format(
                "https://api.powerbi.com/v1.0/myorg/groups/%s/reports/%s/GenerateToken",
                workspaceId, reportId
        );

        // Build the request body
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("accessLevel", "View");

        RequestBody body = RequestBody.create(
                requestBody.toString(),
                MediaType.parse("application/json")
        );

        // Build the HTTP request
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + accessToken)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        // Execute the request
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String errorBody = response.body() != null ? response.body().string() : "No error details";
                throw new IOException("Failed to generate embed token: " + response.code() + " - " + errorBody);
            }

            if (response.body() == null) {
                throw new IOException("Empty response body");
            }

            String responseBody = response.body().string();
            JsonObject jsonResponse = gson.fromJson(responseBody, JsonObject.class);

            // Get the embed URL for the report
            String embedUrl = getReportEmbedUrl(workspaceId, reportId, accessToken);

            // Create and return EmbedConfig
            return new EmbedConfig(
                    jsonResponse.get("token").getAsString(),
                    embedUrl,
                    reportId,
                    jsonResponse.has("tokenId") ? jsonResponse.get("tokenId").getAsString() : null,
                    jsonResponse.has("expiration") ? jsonResponse.get("expiration").getAsString() : null
            );
        }
    }

    /**
     * Get the embed URL for a report
     */
    private String getReportEmbedUrl(String workspaceId, String reportId, String accessToken) throws IOException {
        String url = String.format(
                "https://api.powerbi.com/v1.0/myorg/groups/%s/reports/%s",
                workspaceId, reportId
        );

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + accessToken)
                .get()
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Failed to get report details: " + response.code());
            }

            if (response.body() == null) {
                throw new IOException("Empty response body");
            }

            String responseBody = response.body().string();
            JsonObject jsonResponse = gson.fromJson(responseBody, JsonObject.class);

            return jsonResponse.has("embedUrl") ? jsonResponse.get("embedUrl").getAsString() : "";
        }
    }

    /**
     * Get embed token with dataset access
     */
    public EmbedToken getEmbedTokenForDataset(String datasetId) throws IOException {
        String accessToken = getAccessToken();
        
        String url = String.format(
                "https://api.powerbi.com/v1.0/myorg/datasets/%s/GenerateToken",
                datasetId
        );

        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("accessLevel", "View");

        RequestBody body = RequestBody.create(
                requestBody.toString(),
                MediaType.parse("application/json")
        );

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + accessToken)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String errorBody = response.body() != null ? response.body().string() : "No error details";
                throw new IOException("Failed to generate dataset embed token: " + response.code() + " - " + errorBody);
            }

            if (response.body() == null) {
                throw new IOException("Empty response body");
            }

            String responseBody = response.body().string();
            JsonObject jsonResponse = gson.fromJson(responseBody, JsonObject.class);

            return new EmbedToken(
                    jsonResponse.get("token").getAsString(),
                    jsonResponse.has("tokenId") ? jsonResponse.get("tokenId").getAsString() : null,
                    jsonResponse.has("expiration") ? jsonResponse.get("expiration").getAsString() : null
            );
        }
    }
}
