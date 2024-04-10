package com.karthikeyan.eLearning.service;

import com.karthikeyan.eLearning.model.UserProgress;
import com.karthikeyan.eLearning.repository.UserProgressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserProgressService {

    private static final Logger logger = LoggerFactory.getLogger(UserProgressService.class);

    private final UserProgressRepository userProgressRepository;

    @Autowired
    public UserProgressService(UserProgressRepository userProgressRepository) {
        this.userProgressRepository = userProgressRepository;
    }

    public List<UserProgress> getAllUserProgress() {
        logger.info("Fetching all user progress");
        return userProgressRepository.findAll();
    }

    public UserProgress getUserProgressById(Long id) {
        logger.info("Fetching user progress by ID: {}", id);
        Optional<UserProgress> optionalUserProgress = userProgressRepository.findById(id);
        return optionalUserProgress.orElse(null);
    }

    public UserProgress saveUserProgress(UserProgress userProgress) {
        logger.info("Creating user progress: {}", userProgress);
        return userProgressRepository.save(userProgress);
    }

    public List<UserProgress> getUserProgressByUserId(Long userId) {
        try {
            return userProgressRepository.findByUserId(userId);
        } catch (Exception e) {
            logger.error("Unknown Exception while getUserProgressByUserId : {}", userId);
            return Collections.emptyList();
        }
    }

    public List<UserProgress> getUserProgressByUserIdAndCourseId(Long userId, Long courseId) {
        try {
            return userProgressRepository.findByUserIdAndCourseId(userId, courseId);
        } catch (Exception e) {
            logger.error("Unknown Exception while getUserProgressByUserIdAndCourseId {} : {}", userId, courseId);
            return Collections.emptyList();
        }
    }

    public UserProgress getUserProgressByUserIdAndSectionId(Long userId, Long sectionId) {
        try {
            return userProgressRepository.findByUserIdAndSectionId(userId, sectionId);
        } catch (Exception e) {
            logger.error("Unknown Exception while getUserProgressByUserIdAndSectionId {} : {}", userId, sectionId);
            return null;
        }
    }

    public void saveAllUserProgress(List<UserProgress> userProgressList) {
        logger.info("Creating user progress list : {}", userProgressList.size());
        userProgressRepository.saveAll(userProgressList);
    }

    public boolean deleteUserProgress(Long id) {
        logger.info("Deleting user progress with ID {}", id);
        try {
            userProgressRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            logger.error("Error deleting user progress with ID {}: {}", id, e.getMessage());
            return false;
        }
    }
}
