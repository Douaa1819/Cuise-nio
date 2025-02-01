package com.youcode.cuisinio.user.application.service;

import com.youcode.cuisinio.user.domain.exception.EmailAlreadyExistsException;
import com.youcode.cuisinio.user.domain.model.agregate.User;
import com.youcode.cuisinio.user.domain.model.valueObject.Email;
import com.youcode.cuisinio.user.application.dto.request.RegisterUserRequest;
import com.youcode.cuisinio.user.application.dto.response.UserResponse;
import com.youcode.cuisinio.user.domain.model.valueObject.PasswordHash;
import com.youcode.cuisinio.user.domain.model.valueObject.UserId;
import com.youcode.cuisinio.user.domain.model.valueObject.UserProfile;
import com.youcode.cuisinio.user.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAppService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse registerUser(RegisterUserRequest request) {
        Email email = new Email(request.email());
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException(email.value());
        }

        UserProfile profile = new UserProfile(
                request.firstName(),
                request.lastName(),
                request.birthDate()
        );

        PasswordHash passwordHash = new PasswordHash(
                passwordEncoder.encode(request.password())
        );

        User user = new User(
                UserId.generate(),
                email,
                passwordHash,
                profile
        );

        userRepository.save(user);
        return new UserResponse(
                user.getId().value(),
                user.getEmail().value(),
                user.getProfile().firstName(),
                user.getProfile().lastName()
        );
    }
}