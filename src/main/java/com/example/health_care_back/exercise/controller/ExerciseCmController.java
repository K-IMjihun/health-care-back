package com.example.health_care_back.exercise.controller;

import com.example.health_care_back.application.common.response.CommonResponse;
import com.example.health_care_back.exercise.controller.dto.CreateExerciseDTO;
import com.example.health_care_back.exercise.service.ExerciseCmService;
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
@RequestMapping("/api/v1/cm/exercises")
public class ExerciseCmController {

    private final ExerciseCmService exerciseCmService;

    // 개인 운동 종목 생성
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse<Void> createExercise(@Valid @RequestBody CreateExerciseDTO dto,
                                               @AuthenticationPrincipal LoginUser loginUser) {
        exerciseCmService.createExercise(loginUser, dto);
        return CommonResponse.success();
    }

}
