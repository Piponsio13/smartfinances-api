package io.github.piponsio.smartfinances_api.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.piponsio.smartfinances_api.dto.request.LoginRequestDto;
import io.github.piponsio.smartfinances_api.dto.response.LoginReponseDto;
import io.github.piponsio.smartfinances_api.entity.User;
import io.github.piponsio.smartfinances_api.security.JwtUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService{
    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Override
    public LoginReponseDto login(LoginRequestDto loginRequest) throws Exception {
        try{
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
            Authentication authentication = authenticationManager.authenticate(authToken);
            User user =  (User) authentication.getPrincipal();

            if(user == null){
                throw new Exception("User not found");
            }
            
            String jwtToken = jwtUtils.generateToken(user);

            LoginReponseDto loginReponseDto = new LoginReponseDto();
            loginReponseDto.setEmail(user.getEmail());
            loginReponseDto.setName(user.getName());
            loginReponseDto.setToken(jwtToken);

            return loginReponseDto;
        }catch(BadCredentialsException badCredentialsException){
            logger.error("Bad credentials:", badCredentialsException);
            throw new Exception("Invalid email or password");
        }
    }
}