package com.karthikeyan.eLearning.service;

import com.karthikeyan.eLearning.model.Course;
import com.karthikeyan.eLearning.repository.CourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private static final Logger logger = LoggerFactory.getLogger(CourseService.class);

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses() {
        logger.info("Fetching all courses");
        return courseRepository.findAll();
    }

    public Course getCourseById(Long id) {
        logger.info("Fetching course by ID: {}", id);
        Optional<Course> optionalCourse = courseRepository.findById(id);
        return optionalCourse.orElse(null);
    }

    public Course saveCourse(Course course) {
        logger.info("Creating course: {}", course);
        return courseRepository.save(course);
    }
    public boolean deleteCourse(Long id) {
        logger.info("Deleting course with ID {}", id);
        try {
            courseRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            logger.error("Error deleting course with ID {}: {}", id, e.getMessage());
            return false;
        }
    }
}
