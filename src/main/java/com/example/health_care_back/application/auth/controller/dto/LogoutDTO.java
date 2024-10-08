package com.example.health_care_back.application.auth.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record LogoutDTO(@NotBlank String accessToken) {
}
