package com.example.health_care_back.application.exercise.repository;

import com.example.health_care_back.application.exercise.repository.param.SearchExerciseParam;
import com.example.health_care_back.application.vo.ExerciseVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExerciseRepositoryCustom {

    Page<ExerciseVO> findAll(SearchExerciseParam param, Pageable pageable);

}
