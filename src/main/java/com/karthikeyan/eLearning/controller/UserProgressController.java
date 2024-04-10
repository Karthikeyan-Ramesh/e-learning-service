package com.karthikeyan.eLearning.controller;

import com.karthikeyan.eLearning.model.UserProgress;
import com.karthikeyan.eLearning.service.UserProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user-progress")
public class UserProgressController {

    private final UserProgressService userProgressService;

    @Autowired
    public UserProgressController(UserProgressService userProgressService) {
        this.userProgressService = userProgressService;
    }

    @GetMapping
    public ResponseEntity<List<UserProgress>> getAllUserProgress() {
        List<UserProgress> userProgressList = userProgressService.getAllUserProgress();
        return new ResponseEntity<>(userProgressList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProgress> getUserProgressById(@PathVariable("id") Long id) {
        UserProgress userProgress = userProgressService.getUserProgressById(id);
        if (userProgress != null) {
            return new ResponseEntity<>(userProgress, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<UserProgress> createUserProgress(@Valid @RequestBody UserProgress userProgress,
                                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UserProgress createdUserProgress = userProgressService.saveUserProgress(userProgress);
        return new ResponseEntity<>(createdUserProgress, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserProgress(@PathVariable("id") Long id) {
        boolean deleted = userProgressService.deleteUserProgress(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
