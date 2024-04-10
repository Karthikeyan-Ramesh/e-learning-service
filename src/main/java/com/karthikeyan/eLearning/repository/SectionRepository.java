package com.karthikeyan.eLearning.repository;

import com.karthikeyan.eLearning.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
    List<Section> findByModuleId(Long moduleId);
}
