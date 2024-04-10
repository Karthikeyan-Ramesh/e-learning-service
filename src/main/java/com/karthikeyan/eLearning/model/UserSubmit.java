package com.karthikeyan.eLearning.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSubmit {

    @NotNull
    private Long courseId;
    @NotNull
    private Long sectionId;
    @NotNull
    private Long userId;
    @NotNull
    private String answer;
}
