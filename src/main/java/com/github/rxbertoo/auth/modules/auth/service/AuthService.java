package com.github.rxbertoo.auth.modules.auth.service;

import com.github.rxbertoo.auth.exception.BusinessRuleException;
import com.github.rxbertoo.auth.exception.ErrorCode;
import com.github.rxbertoo.auth.modules.auth.dto.AuthLoginDTO;
import com.github.rxbertoo.auth.modules.auth.dto.AuthRegisterDTO;
import com.github.rxbertoo.auth.modules.auth.dto.AuthResponseDTO;
import com.github.rxbertoo.auth.modules.user.dto.UserCreateDTO;
import com.github.rxbertoo.auth.modules.user.entity.UserEntity;
import com.github.rxbertoo.auth.modules.user.service.UserService;
import com.github.rxbertoo.auth.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    /**
     * Login user and return JWT token.
     *
     * @param dto AuthLoginDto
     */
    public AuthResponseDTO login(AuthLoginDTO dto) {
        if (!userService.existsByEmail(dto.getEmail())) {
            throw new BusinessRuleException(ErrorCode.INVALID_CREDENTIALS);
        }

        UserEntity userEntity = userService.getUserByEmail(dto.getEmail());
        boolean passwordMatches = passwordEncoder.matches(
                dto.getPassword(),
                userEntity.getPassword()
        );

        if (!passwordMatches) {
            throw new BusinessRuleException(ErrorCode.INVALID_CREDENTIALS);
        }

        String token = jwtService.generateToken(userEntity.getUuid());

        AuthResponseDTO authResponse = new AuthResponseDTO();

        authResponse.setToken(token);

        authResponse.setUuid(userEntity.getUuid());
        authResponse.setEmail(userEntity.getEmail());
        authResponse.setName(userEntity.getName());

        return authResponse;
    }

    /**
     * Register user  and create UserEntity.
     *
     * @param dto AuthRegisterDTO
     */
    public void register(AuthRegisterDTO dto) {
        UserCreateDTO userDto = new UserCreateDTO();

        userDto.setName(dto.getName());
        userDto.setEmail(dto.getEmail());
        userDto.setPassword(dto.getPassword());

        userService.createUser(userDto);
    }
}
