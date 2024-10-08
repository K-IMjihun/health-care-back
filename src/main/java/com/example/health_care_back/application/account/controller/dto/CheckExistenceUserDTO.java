package com.example.health_care_back.application.account.controller.dto;

import jakarta.validation.constraints.NotEmpty;

public record CheckExistenceUserDTO(
        @NotEmpty
        String email
) {
}
