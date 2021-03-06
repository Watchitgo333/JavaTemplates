package com.codingdojo.projectmanager.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long Id;
	@NotEmpty(message="First name cannot be blank!")
	@Size(min=2, max=30, message="First Name must be between 2 and 30 characters.")
	private String firstName;
	@NotEmpty(message="Last name cannot be blank!")
	@Size(min=2, max=30, message="Last Name must be between 2 and 30 characters.")
	private String lastName;
	@NotEmpty(message="Email cannot be blank!")
	@Email(message="Please enter a valid email.")
	private String email;
	@NotEmpty(message="Password cannot be blank!")
	@Size(min=8, max=128, message="Password must be between 8 and 128 characters.")
	private String password;
	@Transient
	@NotEmpty(message="Confirm password cannot be blank!")
	@Size(min=8, max=128, message="Confirm password must be between 8 and 128 characters.")
	private String confirm;
	
	@Column(updatable=false)
	private Date createdAt;
	private Date updatedAt;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name="users_projects",
			joinColumns=@JoinColumn(name="user_id"),
			inverseJoinColumns=@JoinColumn(name="project_id"))
	
	private List<Project> projects; 
	
	@Column(updatable=false)
	@OneToMany(mappedBy="leader", fetch=FetchType.LAZY)
	private List<Project> projectsCreated;
	
	@Column(updatable=false)
	@OneToMany(mappedBy="taskCreator", fetch=FetchType.LAZY)
	private List<Task> tasksCreated;
	
	public User() {}
	
//	public User(String firstName, String lastName, String email, String password) {
//		this.firstName = firstName;
//		this.lastName = lastName;
//		this.email = email;
//		this.password = password;
//	}

	public Long getId() {
		return Id;
	}
	
	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public List<Project> getProjectsCreated() {
		return projectsCreated;
	}

	public void setProjectsCreated(List<Project> projectsCreated) {
		this.projectsCreated = projectsCreated;
	}

	public List<Task> getTasksCreated() {
		return tasksCreated;
	}

	public void setTasksCreated(List<Task> tasksCreated) {
		this.tasksCreated = tasksCreated;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new Date();
	}

}