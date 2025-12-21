package org.volumteerhub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.volumteerhub.common.enumeration.UserRole;
import org.volumteerhub.common.exception.ResourceNotFoundException;
import org.volumteerhub.dto.UserResponse;
import org.volumteerhub.service.UserService;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserService userService;

    @PostMapping("/{id}/deactivate")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse deactivateUser(@PathVariable UUID id) {
        return userService.setActiveStageUser(id, Boolean.FALSE);
    }

    @PostMapping("/{id}/activate")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse activateUser(@PathVariable UUID id) {
        return userService.setActiveStageUser(id, Boolean.TRUE);
    }

    @PostMapping("/{id}/set-role/{role}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse setUserRole(@PathVariable UUID id, @PathVariable String role) {
        try {
            UserRole userRole = UserRole.valueOf(role.toUpperCase());
            return userService.setUserRole(id, userRole);
        } catch (IllegalArgumentException e) {
            throw new ResourceNotFoundException("Invalid role: " + role);
        }
    }
}
