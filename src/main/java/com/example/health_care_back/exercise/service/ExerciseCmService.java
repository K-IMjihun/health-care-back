package com.example.health_care_back.exercise.service;

import com.example.health_care_back.application.account.domain.User;
import com.example.health_care_back.application.account.domain.code.UserStatus;
import com.example.health_care_back.application.account.repository.UserRepository;
import com.example.health_care_back.application.common.exception.ResourceException;
import com.example.health_care_back.application.common.exception.ResourceException.ResourceExceptionCode;
import com.example.health_care_back.exercise.controller.dto.CreateExerciseDTO;
import com.example.health_care_back.exercise.helper.ExerciseHelper;
import com.example.health_care_back.infra.config.security.user.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExerciseCmService {

    private final UserRepository userRepository;
    private final ExerciseHelper exerciseHelper;


    @Transactional
    public void createExercise(LoginUser loginUser, CreateExerciseDTO dto) {
        User user = userRepository.findByIdAndUserStatusIs(loginUser.getId(), UserStatus.ACTIVATED)
                .orElseThrow(() -> new ResourceException(ResourceExceptionCode.RESOURCE_NOT_FOUND));

        exerciseHelper.createExercise(user, dto);
    }
}
