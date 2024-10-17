package com.example.health_care_back.application.exercise.service;

import com.example.health_care_back.application.account.domain.User;
import com.example.health_care_back.application.account.domain.code.UserStatus;
import com.example.health_care_back.application.account.repository.UserRepository;
import com.example.health_care_back.application.common.exception.ResourceException;
import com.example.health_care_back.application.common.exception.ResourceException.ResourceExceptionCode;
import com.example.health_care_back.application.exercise.controller.dto.CreateExerciseDTO;
import com.example.health_care_back.application.exercise.helper.ExerciseHelper;
import com.example.health_care_back.application.exercise.repository.ExerciseRepository;
import com.example.health_care_back.infra.config.security.user.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExerciseCmService {

    private final ExerciseRepository exerciseRepository;
    private final UserRepository userRepository;
    private final ExerciseHelper exerciseHelper;

    public void createExercise(LoginUser loginUser, CreateExerciseDTO dto) {
        User user = userRepository.findByIdAndUserStatusIs(loginUser.getId(), UserStatus.ACTIVATED)
            .orElseThrow(() -> new ResourceException(ResourceExceptionCode.RESOURCE_NOT_FOUND));

        exerciseHelper.createExercise(user, dto);
    }
}
