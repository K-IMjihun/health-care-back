package com.example.health_care_back.application.exercise.repository.param;

import com.example.health_care_back.application.account.domain.code.AuthorityType;
import com.example.health_care_back.application.exercise.controller.dto.SearchExerciseDTO;
import com.example.health_care_back.application.exercise.domain.code.ExerciseBodyType;
import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record SearchExerciseParam(
        String exerciseName,
        ExerciseBodyType exerciseBodyType,
        Boolean exerciseDeleted,
        Long userId
) {

    public static SearchExerciseParam valueOf(SearchExerciseDTO dto, @Nullable Long userId, AuthorityType authorityType) {
        var builder = builder()
                .exerciseName(dto.getName())
                .exerciseBodyType(dto.getExerciseBodyType())
                .exerciseDeleted(authorityType == AuthorityType.SYSTEM ?
                        dto.getExerciseDeleted() : Boolean.TRUE);

        if (AuthorityType.getCommonAuthorityTypes().contains(authorityType)) {
            builder.userId(userId);
        }

        return builder.build();
    }
}
