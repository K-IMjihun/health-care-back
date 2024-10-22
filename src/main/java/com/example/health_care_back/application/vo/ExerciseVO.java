package com.example.health_care_back.application.vo;

import com.example.health_care_back.application.account.domain.code.AuthorityType;
import com.example.health_care_back.application.exercise.domain.code.ExerciseBodyType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExerciseVO {

    private Long id;
    private String name;
    private boolean isDeleted;
    private ExerciseBodyType exerciseBodyType;

    private Long userId;
    private AuthorityType authorityType;
}
