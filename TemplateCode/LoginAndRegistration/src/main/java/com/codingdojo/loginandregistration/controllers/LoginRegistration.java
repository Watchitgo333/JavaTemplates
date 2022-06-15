package com.codingdojo.loginandregistration.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.codingdojo.loginandregistration.models.LoginUser;
import com.codingdojo.loginandregistration.models.User;
import com.codingdojo.loginandregistration.repositories.UserService;

@Controller
public class LoginRegistration {

	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("newUser", new User());
		model.addAttribute("newLogin", new LoginUser());
		return "index.jsp";
	}
	
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("newUser")User newUser, BindingResult result, Model model, HttpSession session){
		if(result.hasErrors()) {
			model.addAttribute("newLogin", new LoginUser());
			return "index.jsp";
		} 
		if(userService.register(newUser, result)==null) {
			model.addAttribute("newLogin", new LoginUser());
			return "index.jsp";
		}
		else {
			userService.register(newUser, result);
			session.setAttribute("loggedIn", newUser.getId());
			return "redirect:/home";
		}
	}
	
	@GetMapping("/home")
	public String home(HttpSession session, Model model) {
		if(session.getAttribute("loggedIn")==null) {
			return "redirect:/logout";	
		}
		Long userId = (Long) session.getAttribute("loggedIn");
		User user = userService.findById(userId);
		model.addAttribute("user", user);
		return "home.jsp";
	}
	
	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("newLogin") LoginUser newLogin, BindingResult result, Model model, HttpSession session) {
		User user = userService.loginUser(newLogin, result);
		
		if(result.hasErrors()) {
			model.addAttribute("newUser", new User());
			return "index.jsp";
		} else {
			session.setAttribute("loggedIn", user.getId());
			return "redirect:/home";
		}
	}
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.setAttribute("loggedIn", null);
		return "redirect:/";
	}
}
