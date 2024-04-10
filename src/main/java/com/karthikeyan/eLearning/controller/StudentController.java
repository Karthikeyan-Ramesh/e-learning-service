package com.karthikeyan.eLearning.controller;

import com.karthikeyan.eLearning.constant.LearningConstant;

import com.karthikeyan.eLearning.model.*;
import com.karthikeyan.eLearning.model.Module;
import com.karthikeyan.eLearning.service.ModuleService;
import com.karthikeyan.eLearning.service.SectionService;
import com.karthikeyan.eLearning.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    private final StudentService studentService;
    private final ModuleService moduleService;
    private final SectionService sectionService;
    public StudentController(StudentService studentService, ModuleService moduleService, SectionService sectionService) {
        this.studentService = studentService;
        this.moduleService = moduleService;
        this.sectionService = sectionService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> students = studentService.getUsersByUserType(LearningConstant.STUDENT);
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<User> createStudent(@Valid @RequestBody User user, BindingResult bindingResult) throws MethodArgumentNotValidException {
        if (bindingResult.hasErrors()) {
            logger.error("Invalid student data: {}", bindingResult.getAllErrors());
            throw new MethodArgumentNotValidException(null, bindingResult);
        }
        user.setPassword("12345");
        user.setRoles(LearningConstant.STUDENT);
        User createdUser = studentService.createStudent(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PostMapping("/enrollments")
    public ResponseEntity<?> courseEnrollment(@Valid @RequestBody UserSubmit userSubmit, BindingResult bindingResult) throws MethodArgumentNotValidException {
        if (bindingResult.hasErrors()) {
            logger.error("Invalid enrollment data: {}", bindingResult.getAllErrors());
            throw new MethodArgumentNotValidException(null, bindingResult);
        }
        if(userSubmit.getCourseId() != 0 || userSubmit.getUserId() != 0) {
            List<Module> moduleList = moduleService.getModulesByCourseId(userSubmit.getCourseId());
            if(!moduleList.isEmpty()) {
                studentService.processCourseEnrollment(userSubmit, moduleList);
                return new ResponseEntity<>("Enrolled course properly", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Unable to enroll course, data not found", HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<>(userSubmit, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/quizzes")
    public ResponseEntity<?> submitQuizzes(@Valid @RequestBody UserSubmit userSubmit, BindingResult bindingResult) throws MethodArgumentNotValidException {
        if (bindingResult.hasErrors()) {
            logger.error("Invalid quiz submit data: {}", bindingResult.getAllErrors());
            throw new MethodArgumentNotValidException(null, bindingResult);
        }
        if(userSubmit.getSectionId() != 0 || userSubmit.getUserId() != 0) {
            Section section = sectionService.getSectionById(userSubmit.getSectionId());
            if(null == section) {
                return new ResponseEntity<>("No data found for section ", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(studentService.processQuiz(userSubmit, section), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Incorrect data", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/assignments")
    public ResponseEntity<?> submitAssignment(@Valid @RequestBody UserSubmit userSubmit, BindingResult bindingResult) throws MethodArgumentNotValidException {
        if (bindingResult.hasErrors()) {
            logger.error("Invalid assignment data: {}", bindingResult.getAllErrors());
            throw new MethodArgumentNotValidException(null, bindingResult);
        }
        if(userSubmit.getSectionId() != 0 || userSubmit.getUserId() != 0) {
            Section section = sectionService.getSectionById(userSubmit.getSectionId());
            if(null == section) {
                return new ResponseEntity<>("No data found for assignment ", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(studentService.processAssignment(userSubmit, section), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Incorrect data", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{studentId}/progress")
    public ResponseEntity<?> readUserProgress(@PathVariable("studentId") Long studentId) {

        List<UserProgress> userProgressList = studentService.getUserProgressByUserId(studentId);
        return new ResponseEntity<>(userProgressList, HttpStatus.CREATED);
    }
}
