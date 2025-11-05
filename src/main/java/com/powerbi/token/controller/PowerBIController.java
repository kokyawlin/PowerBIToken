package com.powerbi.token.controller;

import com.powerbi.token.model.EmbedConfig;
import com.powerbi.token.model.EmbedToken;
import com.powerbi.token.service.PowerBIService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/powerbi")
// Note: @CrossOrigin(origins = "*") allows all origins for development/demo purposes.
// For production, configure specific allowed origins or use Spring Security for proper CORS configuration.
@CrossOrigin(origins = "*")
public class PowerBIController {

    private final PowerBIService powerBIService;

    public PowerBIController(PowerBIService powerBIService) {
        this.powerBIService = powerBIService;
    }

    /**
     * Get embed token for a report
     * 
     * @param workspaceId The workspace (group) ID
     * @param reportId The report ID
     * @return EmbedConfig containing the embed token and URL
     */
    @GetMapping("/embedtoken")
    public ResponseEntity<?> getEmbedToken(
            @RequestParam String workspaceId,
            @RequestParam String reportId) {
        try {
            EmbedConfig embedConfig = powerBIService.getEmbedToken(workspaceId, reportId);
            return ResponseEntity.ok(embedConfig);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error generating embed token: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unexpected error: " + e.getMessage());
        }
    }

    /**
     * Get embed token for a dataset
     * 
     * @param datasetId The dataset ID
     * @return EmbedToken containing the token
     */
    @GetMapping("/embedtoken/dataset")
    public ResponseEntity<?> getEmbedTokenForDataset(@RequestParam String datasetId) {
        try {
            EmbedToken embedToken = powerBIService.getEmbedTokenForDataset(datasetId);
            return ResponseEntity.ok(embedToken);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error generating dataset embed token: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unexpected error: " + e.getMessage());
        }
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Power BI Token Service is running");
    }
}
