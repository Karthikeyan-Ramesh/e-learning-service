package com.karthikeyan.eLearning;

import com.karthikeyan.eLearning.constant.LearningConstant;
import com.karthikeyan.eLearning.model.User;
import com.karthikeyan.eLearning.repository.UserRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "E-Learning Application API'S", version = "1.0.0"),
		servers = {@Server(url = "http://localhost:8080/", description = "LOCAL URL")})
public class ELearningApplication {

	public static void main(String[] args) {
		SpringApplication.run(ELearningApplication.class, args);
	}

	@Autowired
	UserRepository userRepository;

	public void insertUsers() {
		List<User> userList = new ArrayList<>();
		userList.add(new User(0L, "admin", "admin123", LearningConstant.ADMIN));
		userList.add(new User(0L, "student", "student123", LearningConstant.STUDENT));
		userList.add(new User(0L, "manager", "manager123", LearningConstant.MANAGER));
		userRepository.saveAll(userList);
	}

}
