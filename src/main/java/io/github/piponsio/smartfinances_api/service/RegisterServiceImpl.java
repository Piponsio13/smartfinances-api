package io.github.piponsio.smartfinances_api.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.piponsio.smartfinances_api.dto.request.RegisterRequestDto;
import io.github.piponsio.smartfinances_api.entity.User;
import io.github.piponsio.smartfinances_api.enums.roleEnum;
import io.github.piponsio.smartfinances_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void registerUser(RegisterRequestDto registerRequestDto) {
        User user = new User();
        user.setName(registerRequestDto.getName());
        user.setEmail(registerRequestDto.getEmail());
        user.addRole(roleEnum.OWNER);

        String hashPassword = passwordEncoder.encode(registerRequestDto.getPassword());
        user.setPassword(hashPassword);
        
        userRepository.save(user);
    }
    
}
