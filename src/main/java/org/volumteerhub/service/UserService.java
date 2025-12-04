package org.volumteerhub.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.volumteerhub.common.UserRole;
import org.volumteerhub.dto.CreateUserRequest;
import org.volumteerhub.dto.UserResponse;
import org.volumteerhub.model.User;
import org.volumteerhub.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserResponse createUser(CreateUserRequest req) {
        User user = User.builder()
                .firstname(req.getFirstname())
                .lastname(req.getLastname())
                .username(req.getUsername())
                .passwordHash(passwordEncoder.encode(req.getPassword()))
                .role(UserRole.USER)
                .build();

        userRepository.save(user);

        return UserResponse.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .username(user.getUsername())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
