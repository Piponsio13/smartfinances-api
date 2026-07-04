package io.github.piponsio.smartfinances_api.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import io.github.piponsio.smartfinances_api.entity.User;

@Component
public class AuthUser {
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }
}
