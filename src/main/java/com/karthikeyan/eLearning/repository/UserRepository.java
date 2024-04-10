package com.karthikeyan.eLearning.repository;

import com.karthikeyan.eLearning.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByRoles(String role);

    User findByUserName(String username);
}
