package com.karthikeyan.eLearning.service;

import com.karthikeyan.eLearning.model.*;
import com.karthikeyan.eLearning.model.Module;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final UserService userService;

    private final SectionService sectionService;

    private final ModuleService moduleService;

    private final UserProgressService userProgressService;


    @Autowired
    public StudentService(UserService userService, SectionService sectionService, ModuleService moduleService, UserProgressService userProgressService) {
        this.userService = userService;
        this.sectionService = sectionService;
        this.moduleService = moduleService;
        this.userProgressService = userProgressService;
    }

    public List<User> getUsersByUserType(String userType) {
        return userService.getUsersByUserType(userType);
    }

    public User createStudent(User user) {
        return userService.saveUser(user);
    }

    public List<UserProgress> getUserProgressByUserId(Long studentId) {
        return userProgressService.getUserProgressByUserId(studentId);
    }

    public List<UserProgress> getUserProgressByUserIdAndCourseId(Long studentId, Long courseId) {
        return userProgressService.getUserProgressByUserIdAndCourseId(studentId, courseId);
    }

    public void processCourseEnrollment(UserSubmit userSubmit, List<Module> moduleList) {
        List<UserProgress> userProgressList = new ArrayList<>();
        for (Module module : moduleList) {
            List<Section> sectionList = sectionService.getSectionsByModuleId(module.getModuleId());
            if(!sectionList.isEmpty()) {
                for (Section section : sectionList) {
                    userProgressList.add(UserProgress.builder()
                            .courseId(userSubmit.getCourseId())
                            .moduleId(module.getModuleId())
                            .sectionId(section.getSectionId())
                            .userId(userSubmit.getUserId())
                            .quizStatus(false)
                            .assignmentStatus(false)
                            .moduleStatus(false)
                            .sectionStatus(false)
                            .courseStatus(false)
                            .build());
                }
            }
            userProgressList.add(UserProgress.builder()
                    .courseId(userSubmit.getCourseId())
                    .moduleId(module.getModuleId())
                    .userId(userSubmit.getUserId())
                    .quizStatus(false)
                    .assignmentStatus(false)
                    .moduleStatus(false)
                    .sectionStatus(false)
                    .courseStatus(false)
                    .build());
        }
        userProgressService.saveAllUserProgress(userProgressList);
    }

    public String processQuiz(UserSubmit userSubmit, Section section) {
        UserProgress userProgress = userProgressService
                .getUserProgressByUserIdAndSectionId(userSubmit.getUserId(), userSubmit.getSectionId());
        if(null != userProgress) {
            userProgress.setQuizUserAnswer(userSubmit.getAnswer());
            userProgress.setQuizStatus(section.getSectionAnswer().equalsIgnoreCase(userSubmit.getAnswer()));
            userProgressService.saveUserProgress(userProgress);
            return "Quiz answer submitted";
        }
        return "No data found for Quiz";
    }

    public String processAssignment(UserSubmit userSubmit, Section section) {
        UserProgress userProgress = userProgressService
                .getUserProgressByUserIdAndSectionId(userSubmit.getUserId(), userSubmit.getSectionId());
        if(null != userProgress) {
            userProgress.setAssignmentUserAnswer(userSubmit.getAnswer());
            userProgress.setAssignmentStatus(section.getSectionAnswer().equalsIgnoreCase(userSubmit.getAnswer()));
            userProgressService.saveUserProgress(userProgress);
            return "Assignment answer submitted";
        }
        return "No data found for Assignment";
    }
}
