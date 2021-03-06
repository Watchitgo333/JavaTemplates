package com.codingdojo.projectmanager.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.codingdojo.projectmanager.models.LoginUser;
import com.codingdojo.projectmanager.models.Project;
import com.codingdojo.projectmanager.models.Task;
import com.codingdojo.projectmanager.models.User;
import com.codingdojo.projectmanager.services.ProjectService;
import com.codingdojo.projectmanager.services.TaskService;
import com.codingdojo.projectmanager.services.UserService;

@Controller
public class HomeController {
	
	@Autowired
	public UserService userService;
	
	@Autowired
	public ProjectService projectService;
	
	@Autowired
	public TaskService taskService;
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("newUser", new User());
		model.addAttribute("newLogin", new LoginUser());
		return "Index.jsp";
	}
	
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("newUser")User newUser, BindingResult result, Model model, HttpSession session) {
		User user = userService.register(newUser, result);
		if(result.hasErrors()) {
			model.addAttribute("newLogin", new LoginUser());
			return "Index.jsp";
		} else {
			session.setAttribute("user_id", user.getId());
			return "redirect:/dashboard";
		}
	}
	
	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("newLogin")LoginUser newLogin, BindingResult result, Model model, HttpSession session) {
		User user = userService.loginUser(newLogin, result);
		if(result.hasErrors()) {
			model.addAttribute("newUser", new User());
			return "Index.jsp";
		} else {
			session.setAttribute("user_id", user.getId());
			
			return "redirect:/dashboard";
		}
	}
	
	@GetMapping("/dashboard")
	public String dashboard(Model model, HttpSession session) {
		if(session.getAttribute("user_id")==null) {
			return "redirect:/logout";
		} else {
			Long user_id = (Long) session.getAttribute("user_id");
			User user = userService.findUserById(user_id);
			model.addAttribute("user", user);
			model.addAttribute("projects", projectService.findAssignedProjects(user));
			model.addAttribute("projectsNotIn", projectService.findUnassignedProjects(user));
			return "Dashboard.jsp";	
		}
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.setAttribute("user_id", null);
		return "redirect:/";
	}
	
	@GetMapping("/projects/new")
	public String newProject(@ModelAttribute("project")Project project, BindingResult result, Model model, HttpSession session) {
		if(session.getAttribute("user_id")==null) {
			return "redirect:/logout";
		} else {
			return "CreateProject.jsp";
		}
	}
	
	@PostMapping("/projects/create")
	public String createProject(@Valid @ModelAttribute("project")Project project, BindingResult result, Model model, HttpSession session) {
		if(session.getAttribute("user_id")==null) {
			return "redirect:/logout";
		} else {
			if(result.hasErrors()) {
				return "CreateProject.jsp";
			} else {
				Long user_id = (Long) session.getAttribute("user_id");
				User user = userService.findUserById(user_id);
				Project newProject = new Project(project.getTitle(), project.getDescription(), project.getDueDate(), project.getLeader());
				newProject.setLeader(user);
				projectService.createProject(newProject);
				user.getProjects().add(newProject);
				userService.updateUser(user);
				return "redirect:/dashboard";
			}
		}
	}
	
	@PostMapping("/join/team/{id}")
	public String joinTeam(@PathVariable("id")Long id, HttpSession session) {
		if(session.getAttribute("user_id")==null) {
			return "redirect:/logout";
		} else {
			Long user_id = (Long) session.getAttribute("user_id");
			User user = userService.findUserById(user_id);
			Project project = projectService.findProjectById(id);
			project.getUsers().add(user);
			projectService.updateProject(project);
			return "redirect:/dashboard";
		}
	}
	
	@PostMapping("/leave/team/{id}")
	public String leaveTeam(@PathVariable("id")Long id, HttpSession session) {
		if(session.getAttribute("user_id")==null) {
			return "redirect:/logout";
		} else {
			Long user_id = (Long) session.getAttribute("user_id");
			User user = userService.findUserById(user_id);
			Project project = projectService.findProjectById(id);
			project.getUsers().remove(user);
			projectService.updateProject(project);
			return "redirect:/dashboard";
		}
	}
	
	@GetMapping("/projects/show/{id}")
	public String showProject(@PathVariable("id")Long id, Model model, HttpSession session) {
		if(session.getAttribute("user_id")==null) {
			return "redirect:/logout";
		} else {
			Long user_id = (Long) session.getAttribute("user_id");
			User userInSesh = userService.findUserById(user_id);
			Project project = projectService.findProjectById(id);
			List<User> users = userService.assignedUsers(project);
			model.addAttribute("project", project);
			model.addAttribute("userInSesh", userInSesh);
			model.addAttribute("users", users);
			return "ProjectDetails.jsp";
		}
	}
	
	@DeleteMapping("/projects/delete/{id}")
	public String deleteProject(@PathVariable("id")Long id, HttpSession session) {
		if(session.getAttribute("user_id")==null) {
			return "redirect:/logout";
		} else {
			projectService.destroyProject(id);
			return "redirect:/dashboard";
			
		}
	}
	
	@GetMapping("/projects/edit/{id}")
	public String editProject(@PathVariable("id")Long id, @ModelAttribute("project")Project project, BindingResult result, Model model, HttpSession session) {
		if(session.getAttribute("user_id")==null) {
			return "redirect:/logout";
		} else {
			Project projectById = projectService.findProjectById(id);
			model.addAttribute("project", projectById);
			return "EditProject.jsp";
		}
	}
	
	@PutMapping("/projects/update/{id}")
	public String updateProject(@PathVariable("id")Long id, @Valid @ModelAttribute("project")Project project, BindingResult result, Model model, HttpSession session) {
		if(session.getAttribute("user_id")==null) {
			return "redirect:/logout";
		} else {
			if(result.hasErrors()) {
				return "EditProject.jsp";
			} else {
				Long user_id = (Long) session.getAttribute("user_id");
				User user = userService.findUserById(user_id);
				Project projectUpdate = projectService.findProjectById(id);
				projectUpdate.setLeader(user);
				projectUpdate.setTitle(project.getTitle());
				projectUpdate.setDescription(project.getDescription());
				projectUpdate.setDueDate(project.getDueDate());
				projectService.updateProject(projectUpdate);
				return "redirect:/dashboard";
			}
		}
	}
	
	@GetMapping("/projects/{id}/tasks")
	public String addTask(@PathVariable("id")Long id, @ModelAttribute("task")Task task, BindingResult result, Model model, HttpSession session) {
		if(session.getAttribute("user_id")==null) {
			return "redirect:/logout";
		} else {
			model.addAttribute("project", projectService.findProjectById(id));
			return "ProjectTasks.jsp";
		}
	}
	
	@PostMapping("/projects/{id}/tasks/create")
	public String createTask(@PathVariable("id")Long id, @ModelAttribute("task")Task task, BindingResult result, Model model, HttpSession session) {
		if(session.getAttribute("user_id")==null) {
			return "redirect:/logout";
		} else {
			if(result.hasErrors()) {
				model.addAttribute("project", projectService.findProjectById(id));
				return "ProjectTasks.jsp";
			} else {
				Task newTask = new Task(task.getTaskName());
				Long user_id = (Long) session.getAttribute("user_id");
				User user = userService.findUserById(user_id);
				Project project = projectService.findProjectById(id);
				newTask.setUser(user);
				newTask.setProject(project);
				taskService.createTask(newTask);
				return String.format("redirect:/projects/%s/tasks", id);
			}
		}
	}
}
