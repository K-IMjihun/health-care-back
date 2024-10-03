package com.example.health_care_back.application.auth.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReissueTokenDTO(
  @NotNull
  String email,
  @NotBlank
  String accessToken,
  @NotBlank
  String refreshToken) {
}
