package com.example.health_care_back.application.exercise.repository;

import com.example.health_care_back.application.exercise.domain.ExerciseTypeRelation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseTypeRelationRepository extends JpaRepository<ExerciseTypeRelation, Long> {
}