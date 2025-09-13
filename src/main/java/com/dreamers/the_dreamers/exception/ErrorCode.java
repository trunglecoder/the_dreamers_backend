package com.dreamers.the_dreamers.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // Authentication errors
    INVALID_CREDENTIALS("AUTH001", "Invalid username or password"),
    USER_NOT_FOUND("AUTH002", "User not found"),
    USER_ALREADY_EXISTS("AUTH003", "User already exists"),
    EMAIL_ALREADY_EXISTS("AUTH004", "Email already exists"),
    USERNAME_ALREADY_EXISTS("AUTH005", "Username already exists"),
    ACCOUNT_NOT_VERIFIED("AUTH006", "Account not verified"),
    TOKEN_EXPIRED("AUTH007", "Token expired"),
    INVALID_TOKEN("AUTH008", "Invalid token"),
    UNAUTHORIZED("AUTH009", "Unauthorized access"),
    FORBIDDEN("AUTH010", "Access forbidden"),
    ROLE_NOT_FOUND("AUTH011", "Role not found"),
    INVALID_VERIFICATION_TOKEN("AUTH012", "Invalid verification token"),
    INVALID_RESET_PASSWORD_TOKEN("AUTH013", "Invalid reset password token"),
    RESET_PASSWORD_EXPIRED("AUTH014", "Reset password expired"),
    INVALID_RESET_TOKEN("AUTH015", "Invalid reset token"),
    RESET_TOKEN_EXPIRED("AUTH016", "Reset token expired"),
    
    // Validation errors
    INVALID_INPUT("VAL001", "Invalid input data"),
    MISSING_REQUIRED_FIELD("VAL002", "Missing required field"),
    INVALID_EMAIL_FORMAT("VAL003", "Invalid email format"),
    INVALID_PASSWORD_FORMAT("VAL004", "Invalid password format"),
    
    // Resource errors
    RESOURCE_NOT_FOUND("RES001", "Resource not found"),
    RESOURCE_ALREADY_EXISTS("RES002", "Resource already exists"),
    RESOURCE_DELETION_FAILED("RES003", "Failed to delete resource"),
    
    // Server errors
    INTERNAL_SERVER_ERROR("SRV001", "Internal server error"),
    DATABASE_ERROR("SRV002", "Database error"),
    EXTERNAL_SERVICE_ERROR("SRV003", "External service error"),
    
    // Business logic errors
    INSUFFICIENT_PERMISSIONS("BUS001", "Insufficient permissions"),
    INVALID_OPERATION("BUS002", "Invalid operation"),
    BUSINESS_RULE_VIOLATION("BUS003", "Business rule violation"),
    EMAIL_SENDING_FAILED("BUS004", "Email sending failed")

    ;

    private final String errorCode;
    private final String errorMessage;
}
