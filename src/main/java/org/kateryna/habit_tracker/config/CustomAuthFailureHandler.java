package org.kateryna.habit_tracker.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException, ServletException {

        String errorParam;

        if (exception instanceof UsernameNotFoundException) {
            errorParam = "no_user";
        } else if (exception instanceof BadCredentialsException) {
            errorParam = "bad_password";
        } else {
            errorParam = "unknown";
        }

        response.sendRedirect("/login?error=" + errorParam);
    }
}

