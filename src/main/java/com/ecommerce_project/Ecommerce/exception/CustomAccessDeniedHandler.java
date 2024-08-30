package com.ecommerce_project.Ecommerce.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        // Set the response status to 403 Forbidden
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        // Write a custom message to the response body
        response.getWriter().write("Access Denied: You do not have the necessary permissions to access this resource.");
    }
}
