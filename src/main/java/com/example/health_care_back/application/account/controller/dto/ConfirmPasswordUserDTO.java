package com.example.health_care_back.application.account.controller.dto;


import jakarta.validation.constraints.NotBlank;

public record ConfirmPasswordUserDTO(
        @NotBlank String password) {
}