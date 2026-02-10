package com.abbtech.dto.security;

import com.abbtech.annotations.LogIgnore;

public record TokenResponseDto(
        @LogIgnore
        String accessToken,
        @LogIgnore
        String refreshToken
) {}

