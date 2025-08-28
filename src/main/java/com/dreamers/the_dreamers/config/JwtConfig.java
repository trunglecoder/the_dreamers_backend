package com.dreamers.the_dreamers.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {
    
    /**
     * JWT Secret Key for signing tokens
     * In production, this should be stored in environment variables
     */
    private String secretKey = "5ygyOVnyfL0hlFRfCsi3ViVFvoXsvOvb0WFXwHZGEPdXPWuPtQGoyfpCeVJjWyvm";
    
    /**
     * JWT Token expiration time in milliseconds (default: 1 hour)
     */
    private long expirationTime = 3600000; // 1 hour
    
    /**
     * JWT Refresh token expiration time in milliseconds (default: 7 days)
     */
    private long refreshExpirationTime = 604800000; // 7 days
    
    /**
     * JWT Token issuer
     */
    private String issuer = "TheDreamers";
    
    /**
     * JWT Token audience
     */
    private String audience = "TheDreamersUsers";
}
