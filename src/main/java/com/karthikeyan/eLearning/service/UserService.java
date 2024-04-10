package com.karthikeyan.eLearning.service;

import com.karthikeyan.eLearning.model.User;
import com.karthikeyan.eLearning.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        logger.info("Fetching all users");
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        logger.info("Fetching user by ID: {}", id);
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }

    public User saveUser(User user) {
        logger.info("Creating user: {}", user);
        return userRepository.save(user);
    }

    public List<User> getUsersByUserType(String userType) {
        try {
            return userRepository.findByRoles(userType);
        } catch (Exception e) {
            logger.error("Unknown Exception while getUsersByUserType : {}", userType);
            return Collections.emptyList();
        }
    }

    public boolean deleteUser(Long id) {
        logger.info("Deleting user with ID {}", id);
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            logger.error("Error deleting user with ID {}: {}", id, e.getMessage());
            return false;
        }
    }
}
