package com.example.health_care_back.application.account.controller.dto;

import com.example.health_care_back.application.account.domain.code.AuthorityType;
import jakarta.validation.constraints.NotNull;

public record AuthorityUpdateDTO(
        @NotNull String email,
        @NotNull AuthorityType authorityType,
        @NotNull String systemKey
) {
}
