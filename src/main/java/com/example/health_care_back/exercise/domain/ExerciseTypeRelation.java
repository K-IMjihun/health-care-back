package com.example.health_care_back.exercise.domain;

import com.example.health_care_back.application.common.domain.Base;
import com.example.health_care_back.exercise.domain.code.ExerciseType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ExerciseTypeRelation extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exercise_type_relation_id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "exercise_type", nullable = false, length = 191)
    private ExerciseType exerciseType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id", nullable = false, referencedColumnName = "exercise_id")
    @JsonBackReference
    private Exercise exercise;

    public static ExerciseTypeRelation createExerciseTypeRelation(ExerciseType exerciseType, Exercise exercise) {
        return ExerciseTypeRelation.builder()
                .exerciseType(exerciseType)
                .exercise(exercise)
                .build();
    }
}