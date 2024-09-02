package com.ecommerce_project.Ecommerce.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

@Data
@Getter
@Setter
public class AuthResponseDTO {
    private String tokenType = "Bearer ";
    private String accessToken;

    public AuthResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }
}
