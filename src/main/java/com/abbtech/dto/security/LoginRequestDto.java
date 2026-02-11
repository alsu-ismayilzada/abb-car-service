package com.abbtech.dto.security;

import com.abbtech.annotations.LogIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto{
        String username;
        @LogIgnore
        String password;
}
