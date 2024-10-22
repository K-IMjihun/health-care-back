package com.example.health_care_back.application.exercise.service;

import com.example.health_care_back.application.account.domain.User;
import com.example.health_care_back.application.account.domain.code.AuthorityType;
import com.example.health_care_back.application.account.domain.code.UserStatus;
import com.example.health_care_back.application.account.repository.UserRepository;
import com.example.health_care_back.application.common.exception.ResourceException;
import com.example.health_care_back.application.common.exception.ResourceException.ResourceExceptionCode;
import com.example.health_care_back.application.exercise.controller.dto.CreateExerciseDTO;
import com.example.health_care_back.application.exercise.controller.dto.SearchExerciseDTO;
import com.example.health_care_back.application.exercise.helper.ExerciseHelper;
import com.example.health_care_back.application.exercise.repository.ExerciseRepository;
import com.example.health_care_back.application.exercise.repository.param.SearchExerciseParam;
import com.example.health_care_back.application.vo.ExerciseVO;
import com.example.health_care_back.infra.config.security.user.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExerciseSyService {

    private final UserRepository userRepository;
    private final ExerciseHelper exerciseHelper;
    private final ExerciseRepository exerciseRepository;

    @Transactional
    public void createExercise(LoginUser loginUser, CreateExerciseDTO dto) {
        User user = userRepository.findByIdAndUserStatusIs(loginUser.getId(), UserStatus.ACTIVATED)
                .orElseThrow(() -> new ResourceException(ResourceExceptionCode.RESOURCE_NOT_FOUND));

        exerciseHelper.createExercise(user, dto);
    }

    public Page<ExerciseVO> getExercises(SearchExerciseDTO dto) {
        return exerciseRepository.findAll(
                SearchExerciseParam.valueOf(dto, null, AuthorityType.SYSTEM),
                dto.toPageRequest());
    }

}
