package com.karthikeyan.eLearning.repository;

import com.karthikeyan.eLearning.model.UserProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProgressRepository extends JpaRepository<UserProgress, Long> {

    List<UserProgress> findByUserId(Long userId);
    List<UserProgress> findByUserIdAndCourseId(Long userId, Long courseId);
    UserProgress findByUserIdAndSectionId(Long userId, Long sectionId);
}
