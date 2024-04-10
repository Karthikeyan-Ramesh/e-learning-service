package com.karthikeyan.eLearning.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_progress")
public class UserProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userProgressId;
    @NotNull
    private Long userId;
    @NotNull
    private Long courseId;
    @NotNull
    private Long moduleId;
    private Long sectionId;

    private String quizUserAnswer;
    private boolean quizStatus;
    private String assignmentUserAnswer;
    private boolean assignmentStatus;  //success or Error
    private boolean moduleStatus;    //start, in_progress, completed
    private boolean sectionStatus;    //start, in_progress, completed
    private boolean courseStatus;  //start, in_progress, completed
}
