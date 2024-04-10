package com.karthikeyan.eLearning.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "module")
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long moduleId;
    @NotBlank
    private String moduleName;
    @NotNull
    private Integer moduleOrder;
    private String moduleType;  //module, quiz, assignment
    @NotNull
    private Long courseId;
}

