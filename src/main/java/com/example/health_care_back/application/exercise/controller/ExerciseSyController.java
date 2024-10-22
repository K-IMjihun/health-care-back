package com.example.health_care_back.application.exercise.controller;

import com.example.health_care_back.application.common.response.CommonResponse;
import com.example.health_care_back.application.exercise.controller.dto.CreateExerciseDTO;
import com.example.health_care_back.application.exercise.controller.dto.SearchExerciseDTO;
import com.example.health_care_back.application.exercise.service.ExerciseSyService;
import com.example.health_care_back.application.vo.ExerciseVO;
import com.example.health_care_back.infra.config.security.user.LoginUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sy/exercises")
public class ExerciseSyController {

    private final ExerciseSyService exerciseSyService;

    // 운동종목 추가
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse<Void> createExercise(@Valid @RequestBody CreateExerciseDTO dto,
                                               @AuthenticationPrincipal LoginUser loginUser) {
        exerciseSyService.createExercise(loginUser, dto);
        return CommonResponse.success();
    }

    // 운동종목 조회
    @GetMapping
    public CommonResponse<Page<ExerciseVO>> getExercises(@Valid SearchExerciseDTO dto) {
        return CommonResponse.success(exerciseSyService.getExercises(dto));
    }
}