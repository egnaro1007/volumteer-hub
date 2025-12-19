package org.volumteerhub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.volumteerhub.common.enumeration.UserRole;
import org.volumteerhub.common.validation.OnCreate;
import org.volumteerhub.common.validation.OnUpdate;
import org.volumteerhub.dto.CreateUserRequest;
import org.volumteerhub.dto.UserResponse;
import org.volumteerhub.service.UserService;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@Validated(OnCreate.class) @RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }

    @GetMapping
    public Page<UserResponse> listUsers(
            @RequestParam(required = false) UserRole role,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(required = false) String username,
            Pageable pageable) {
        return userService.list(role, isActive, username, pageable);
    }

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable UUID id) {
        return userService.get(id);
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(
            @PathVariable UUID id,
            @Validated(OnUpdate.class) @RequestBody CreateUserRequest request) {
        return userService.update(id, request);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser() {
        userService.delete();
    }
}
