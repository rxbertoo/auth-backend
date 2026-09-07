package com.github.rxbertoo.auth.modules.user.service;

import com.github.rxbertoo.auth.exception.BusinessRuleException;
import com.github.rxbertoo.auth.exception.ErrorCode;
import com.github.rxbertoo.auth.modules.user.dto.UserCreateDTO;
import com.github.rxbertoo.auth.modules.user.entity.UserEntity;
import com.github.rxbertoo.auth.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Check if the current email is existing in the database. If exists, return true, else return false.
     *
     * @param email Email string
     * @return boolean
     */
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Get user entity by email
     *
     * @param email Email to get user
     * @return UserEntity
     */
    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessRuleException(ErrorCode.USER_NOT_FOUND));
    }

    /**
     * Get user entity by UUID
     *
     * @param uuid User UUID
     * @return UserEntity
     */
    public UserEntity getUserByUUID(UUID uuid) {
        return userRepository.findByUuid(uuid)
                .orElseThrow(() -> new BusinessRuleException(ErrorCode.USER_NOT_FOUND));
    }

    /**
     * Create user and save to database. If email already exists, throw BusinessRuleException.
     *
     * @param dto UserCreateDTO
     */
    public void createUser(UserCreateDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new BusinessRuleException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        UserEntity user = new UserEntity();

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        String password = passwordEncoder.encode(dto.getPassword());
        user.setPassword(password);

        userRepository.save(user);
    }
}
