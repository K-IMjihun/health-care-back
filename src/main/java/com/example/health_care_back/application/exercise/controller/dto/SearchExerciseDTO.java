package com.example.health_care_back.application.exercise.controller.dto;

import com.example.health_care_back.application.exercise.domain.code.ExerciseBodyType;
import com.example.health_care_back.util.dto.PagingDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchExerciseDTO extends PagingDTO {

    private String name;
    private ExerciseBodyType exerciseBodyType;
    private Boolean exerciseDeleted;

}