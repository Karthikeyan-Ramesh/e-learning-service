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
@Table(name = "section")
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sectionId;
    @NotNull
    private Long moduleId;
    @NotNull
    private String sectionName;
    private String sectionType;   //quiz or assignment
    private String sectionQuestion;
    private String sectionDescription;
    private String sectionAnswer;
    @NotNull
    private Integer sectionOrder;
}
