package com.karthikeyan.eLearning.service;

import com.karthikeyan.eLearning.model.Section;
import com.karthikeyan.eLearning.repository.SectionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class SectionService {

    private static final Logger logger = LoggerFactory.getLogger(SectionService.class);

    private final SectionRepository sectionRepository;

    @Autowired
    public SectionService(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    public List<Section> getAllSections() {
        logger.info("Fetching all sections");
        return sectionRepository.findAll();
    }

    public Section getSectionById(Long id) {
        logger.info("Fetching section by ID: {}", id);
        Section section = sectionRepository.findById(id).orElse(null);
        return section;
    }

    public Section saveSection(Section section) {
        logger.info("Creating section: {}", section);
        return sectionRepository.save(section);
    }

    public List<Section> getSectionsByModuleId(Long moduleId) {
        try {
            return sectionRepository.findByModuleId(moduleId);
        } catch (Exception e) {
            logger.error("Unknown Exception while getSectionsByModuleId : {}", moduleId);
            return Collections.emptyList();
        }
    }
    public boolean deleteSection(Long id) {
        logger.info("Deleting section with ID {}", id);
        try {
            sectionRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            logger.error("Error deleting section with ID {}: {}", id, e.getMessage());
            return false;
        }
    }
}
