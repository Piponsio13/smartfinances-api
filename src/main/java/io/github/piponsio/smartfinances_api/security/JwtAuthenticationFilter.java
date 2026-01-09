package io.github.piponsio.smartfinances_api.security;

import java.io.IOException;
import java.util.logging.Logger;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.github.piponsio.smartfinances_api.entity.User;
import io.github.piponsio.smartfinances_api.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService customUserDetailsService;

    private final Logger loggs = Logger.getLogger(JwtAuthenticationFilter.class.getName());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

                String authHeader = request.getHeader("Authorization");
                String token = null;
                String username = null;

                if(authHeader != null && authHeader.startsWith("Bearer ")){
                    token = authHeader.substring(7);
                    try {
                        username = jwtUtils.extractUsername(token);
                    } catch (Exception e) {
                        loggs.warning("Invalid JWT Token: " + e.getMessage());
                    }
                }
                
                if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                    try {
                        User user = (User) customUserDetailsService.loadUserByUsername(username);

                        if(jwtUtils.validateToken(token, user)){
                            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authToken);
                        }
                    } catch (Exception e) {
                        loggs.warning("Authentication failed: " + e.getMessage());

                    }
                }

                filterChain.doFilter(request, response);
    }
}
