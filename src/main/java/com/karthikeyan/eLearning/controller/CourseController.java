package com.karthikeyan.eLearning.controller;

import com.karthikeyan.eLearning.model.Course;
import com.karthikeyan.eLearning.model.Module;
import com.karthikeyan.eLearning.service.CourseService;
import com.karthikeyan.eLearning.service.ModuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    private final CourseService courseService;
    private final ModuleService moduleService;

    @Autowired
    public CourseController(CourseService courseService, ModuleService moduleService) {
        this.courseService = courseService;
        this.moduleService = moduleService;
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        logger.info("Fetching all courses");
        List<Course> courses = courseService.getAllCourses();

        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<Course> getCourseById(@PathVariable("courseId") Long id) {
        logger.info("Fetching course by ID: {}", id);
        Course course = courseService.getCourseById(id);
        if (course != null) {
            return new ResponseEntity<>(course, HttpStatus.OK);
        } else {
            logger.warn("Course with ID {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@Valid @RequestBody Course course, BindingResult bindingResult) throws MethodArgumentNotValidException {
        if (bindingResult.hasErrors()) {
            logger.error("Invalid course data: {}", bindingResult.getAllErrors());
            throw new MethodArgumentNotValidException(null, bindingResult);
        }
        logger.info("Creating course: {}", course);
        Course createdCourse = courseService.saveCourse(course);
        return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
    }

    @PostMapping("/{courseId}/modules")
    public ResponseEntity<Module> createModule(@PathVariable("courseId") Long id,
                                               @Valid @RequestBody Module module, BindingResult bindingResult) throws MethodArgumentNotValidException {
        if (bindingResult.hasErrors()) {
            logger.error("Invalid course module data: {}", bindingResult.getAllErrors());
            throw new MethodArgumentNotValidException(null, bindingResult);
        }
        module.setCourseId(id);
        Module createdModule = moduleService.saveModule(module);
        return new ResponseEntity<>(createdModule, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable("id") Long id) {
        logger.info("Deleting course with ID {}", id);
        boolean deleted = courseService.deleteCourse(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            logger.warn("Course with ID {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
