package com.codingdojo.loginandregistration.repositories;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.codingdojo.loginandregistration.models.LoginUser;
import com.codingdojo.loginandregistration.models.User;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public User createUser(User newUser) {
		return userRepository.save(newUser);
	}
	
	public User register(User newUser, BindingResult result) {
		Optional<User> potentialUser = userRepository.findByEmail(newUser.getEmail());
		if(potentialUser.isPresent()) {
			result.rejectValue("email", "Matches", "This email is already registered!");
		} 
		
		if(!newUser.getPassword().equals(newUser.getConfirm())) {
			result.rejectValue("confirm", "Matches", "Passwords must match!");
		}
		
		if(result.hasErrors()) {
			return null;
		}
		
		else {
			String hashed = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());
			newUser.setPassword(hashed);
			return userRepository.save(newUser);
		}
	}
	
	public User loginUser(LoginUser newLogin, BindingResult result) {
		Optional<User> potentialUser = userRepository.findByEmail(newLogin.getEmail());
		if(!potentialUser.isPresent()) {
			result.rejectValue("email", "Matches", "User not found");
			return null;	
		}
			User user = potentialUser.get();
		if(!BCrypt.checkpw(newLogin.getPassword(), user.getPassword())) {
			result.rejectValue("password", "Matches", "Invalid Password");
			
		}
		
		if(result.hasErrors()) {
			return null;
		} else {
			return potentialUser.get();
		}
		
	}
	
	public User findById(Long id) {
		Optional<User> potentialUser = userRepository.findById(id);
		if(potentialUser.isPresent()) {
			return potentialUser.get();
		} else {
			return null;
		}
	}
	
}
