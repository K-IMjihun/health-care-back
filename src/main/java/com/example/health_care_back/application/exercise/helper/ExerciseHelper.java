package com.example.health_care_back.application.exercise.helper;
import com.example.health_care_back.application.account.domain.User;
import com.example.health_care_back.application.exercise.controller.dto.CreateExerciseDTO;
import com.example.health_care_back.application.exercise.domain.Exercise;
import com.example.health_care_back.application.exercise.domain.ExerciseTypeRelation;
import com.example.health_care_back.application.exercise.domain.code.ExerciseType;
import com.example.health_care_back.application.exercise.repository.ExerciseRepository;
import com.example.health_care_back.application.exercise.repository.ExerciseTypeRelationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Component
@RequiredArgsConstructor
public class ExerciseHelper {

  private final ExerciseRepository exerciseRepository;
  private final ExerciseTypeRelationRepository exerciseTypeRelationRepository;

  @Transactional
  public void createExercise(User user, CreateExerciseDTO dto) {
    Exercise exercise = Exercise.createExercise(dto, user);
    exerciseRepository.save(exercise);

    List<ExerciseTypeRelation> relations = new ArrayList<>();
    for (ExerciseType exerciseType : dto.exerciseTypes()) {
      relations.add(ExerciseTypeRelation.createExerciseTypeRelation(exerciseType, exercise));
    }
    exerciseTypeRelationRepository.saveAll(relations);
  }
}
