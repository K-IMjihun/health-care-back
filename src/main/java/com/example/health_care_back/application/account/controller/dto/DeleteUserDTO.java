package com.example.health_care_back.application.account.controller.dto;

import com.example.health_care_back.application.account.domain.code.UserStatus;
import com.example.health_care_back.application.common.exception.InvalidInputValueException;
import com.example.health_care_back.application.common.exception.InvalidInputValueException.InvalidInputValueExceptionCode;
import jakarta.validation.constraints.NotNull;

public record DeleteUserDTO(
        @NotNull UserStatus userStatus
) {

    public DeleteUserDTO {
        if (!UserStatus.getSuspendedStatuses().contains(userStatus)) {
            throw new InvalidInputValueException(InvalidInputValueExceptionCode.INVALID_INPUT_VALUE);
        }
    }
}
