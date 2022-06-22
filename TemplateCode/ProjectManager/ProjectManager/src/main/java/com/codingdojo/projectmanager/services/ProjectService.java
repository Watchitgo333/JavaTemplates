package com.codingdojo.projectmanager.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingdojo.projectmanager.models.Project;
import com.codingdojo.projectmanager.models.User;
import com.codingdojo.projectmanager.repositories.ProjectRepository;

@Service
public class ProjectService {
	
	@Autowired
	public ProjectRepository projectRepository;
	
	public ProjectService(ProjectRepository projectRepository) {
		this.projectRepository = projectRepository;
	}
	
	public Project createProject(Project project) {
		return projectRepository.save(project);
	}
	
	public List<Project> findAssignedProjects(User user){
		return projectRepository.findAllByUsers(user);
	}
	
	public List<Project> findUnassignedProjects(User user){
		return projectRepository.findByUsersNotContains(user);
	}
	
	public Project findProjectById(Long id) {
		Optional<Project> potentialProject = projectRepository.findById(id);
		if(potentialProject.isPresent()) {
			return potentialProject.get();
		} else {
			return null;
		}
	}
	
	public Project updateProject(Project project) {
		return projectRepository.save(project);
	}
	
	public void destroyProject(Long id) {
		Optional<Project> potentialProject = projectRepository.findById(id);
		if(potentialProject.isPresent()) {
			projectRepository.deleteById(id);
		}
	}

}
