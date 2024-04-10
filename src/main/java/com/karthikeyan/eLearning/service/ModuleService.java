package com.karthikeyan.eLearning.service;

import com.karthikeyan.eLearning.model.Module;
import com.karthikeyan.eLearning.repository.ModuleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ModuleService {

    private static final Logger logger = LoggerFactory.getLogger(ModuleService.class);

    private final ModuleRepository moduleRepository;

    @Autowired
    public ModuleService(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    public List<Module> getAllModules() {
        logger.info("Fetching all modules");
        return moduleRepository.findAll();
    }

    public Module getModuleById(Long id) {
        logger.info("Fetching module by ID: {}", id);
        Optional<Module> optionalModule = moduleRepository.findById(id);
        return optionalModule.orElse(null);
    }

    public Module saveModule(Module module) {
        logger.info("Save module: {}", module);
        return moduleRepository.save(module);
    }

    public List<Module> getModulesByCourseId(Long courseId) {
        try {
            return moduleRepository.findByCourseId(courseId);
        } catch (Exception e) {
            logger.error("Unknown Exception while getModulesByCourseId : {}", courseId);
            return Collections.emptyList();
        }
    }
    public boolean deleteModule(Long id) {
        logger.info("Deleting module with ID {}", id);
        try {
            moduleRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            logger.error("Error deleting module with ID {}: {}", id, e.getMessage());
            return false;
        }
    }
}
