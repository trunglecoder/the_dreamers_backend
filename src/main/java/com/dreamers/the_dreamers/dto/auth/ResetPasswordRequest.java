package com.dreamers.the_dreamers.dto.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ResetPasswordRequest {
    private String token;
    private String newPassword;
}
