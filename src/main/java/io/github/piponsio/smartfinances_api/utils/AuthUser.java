package io.github.piponsio.smartfinances_api.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import io.github.piponsio.smartfinances_api.entity.User;
import io.github.piponsio.smartfinances_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthUser {
    private final UserRepository userRepository;
    
    public User getAuthenticatedUser(){ 
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();
        
        User user = userRepository.findById(authenticatedUser.getId())
            .orElseThrow(() -> new RuntimeException("User not found"));

        return user;
    }
}
