package com.example.health_care_back.exercise.repository;

import com.example.health_care_back.exercise.domain.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

}