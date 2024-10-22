package com.example.health_care_back.application.exercise.controller;

import com.example.health_care_back.application.common.response.CommonResponse;
import com.example.health_care_back.application.exercise.controller.dto.CreateExerciseDTO;
import com.example.health_care_back.application.exercise.service.ExerciseSyService;
import com.example.health_care_back.infra.config.security.user.LoginUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sy/exercises")
public class ExerciseSyController {

    private final ExerciseSyService exerciseSyService;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse<Void> createExercise(@Valid @RequestBody CreateExerciseDTO dto,
                                               @AuthenticationPrincipal LoginUser loginUser) {
        exerciseSyService.createExercise(loginUser, dto);
        return CommonResponse.success();
    }
}