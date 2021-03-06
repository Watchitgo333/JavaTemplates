package com.codingdojo.projectmanager.services;

import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.codingdojo.projectmanager.models.LoginUser;
import com.codingdojo.projectmanager.models.Project;
import com.codingdojo.projectmanager.models.User;
import com.codingdojo.projectmanager.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	public UserRepository userRepository;
	
	public UserService (UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public User updateUser(User user) {
		return userRepository.save(user);
	}
	
	public User findUserById(Long id) {
		Optional<User> potentialUser = userRepository.findById(id);
		if(potentialUser.isPresent()) {
			return potentialUser.get();
		} else {
			return null;
		}
	}
	
	public User register(User newUser, BindingResult result) {
		Optional<User> potentialUser = userRepository.findByEmail(newUser.getEmail());
		if(potentialUser.isPresent()) {
			result.rejectValue("email", "Matches", "Email already registered!");
		}
		if(!newUser.getPassword().equals(newUser.getConfirm())) {
			result.rejectValue("confirm", "Matches", "Passwords must match!");
		}
		if(result.hasErrors()) {
			return null;
		}
		String hashed = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());
		newUser.setPassword(hashed);
		return userRepository.save(newUser);
	}
	public User loginUser(LoginUser newLogin, BindingResult result) {
		Optional<User> potentialUser = userRepository.findByEmail(newLogin.getEmail());
		if(!potentialUser.isPresent()) {
			result.rejectValue("email", "Matches", "Email not found");
			return null;
		}	
		User user = potentialUser.get();
		if(!BCrypt.checkpw(newLogin.getPassword(), user.getPassword())) {
			result.rejectValue("password", "Matches", "Invalid password");
			return null;
		}
		if(result.hasErrors()) {
			return null;
		} else {
			return user;
		}
	}
	
	public List<User> assignedUsers(Project project){
		return userRepository.findAllByProjects(project);
	}

}
