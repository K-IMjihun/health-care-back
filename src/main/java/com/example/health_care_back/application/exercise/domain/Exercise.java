package com.example.health_care_back.application.exercise.domain;


import com.example.health_care_back.application.account.domain.User;
import com.example.health_care_back.application.common.domain.Base;
import com.example.health_care_back.application.exercise.controller.dto.CreateExerciseDTO;
import com.example.health_care_back.application.exercise.domain.code.ExerciseBodyType;
import com.example.health_care_back.application.exercise.domain.code.ExerciseToolType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;

@Entity
@DynamicUpdate
@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "exercise",
        indexes = {
                @Index(columnList = "exercise_body_type, user_id")
        }
)
@SQLDelete(sql = "UPDATE exercise SET is_deleted = true WHERE exercise_id = ?")
public class Exercise extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exercise_id")
    private Long id;

    @Column(name = "name", nullable = false, length = 191)
    private String name;

    @Column(name = "description", length = 3000)
    private String description;

    private boolean isDeleted;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "exercise_body_type", nullable = false, length = 191)
    private ExerciseBodyType bodyType;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "exercise_tool_type", nullable = false, length = 191)
    private ExerciseToolType toolType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "user_id")
    private User createdUser;

    public static Exercise createExercise(CreateExerciseDTO dto, User user) {
        return builder()
                .name(dto.name())
                .description(dto.description())
                .isDeleted(Boolean.FALSE)
                .bodyType(dto.bodyType())
                .toolType(dto.toolType())
                .createdUser(user)
                .build();
    }
}
