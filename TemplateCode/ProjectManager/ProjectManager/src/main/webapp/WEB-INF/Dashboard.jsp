<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true"%>
<!-- CSS only -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Project Dashboard</title>
</head>
<body>
	<div class="mx-auto col-4 d-flex justify-content-between">
		<h2 class="m-4">Welcome, ${user.firstName}!</h2>
		<form action="/logout" method="GET">
			<button type="submit" class="btn btn-dark m-4">Logout</button>
		</form>
	</div>
	<div class="mx-auto col-4 d-flex justify-content-between">
		<h4 class="m-4">All Projects</h4>
		<form action="/projects/new" method="GET">
			<button type="submit" class="btn btn-primary m-4">+ New project</button>
		</form>
	</div>
	<div class="container">
		<div>
			<table class="table">
			  <thead>
			    <tr>
			      <th scope="col">Project</th>
			      <th scope="col">Team Lead</th>
			      <th scope="col">Due Date</th>
			      <th scope="col">Actions</th>
			    </tr>
			  </thead>
			  <tbody>
			  <c:forEach var="project" items="${projectsNotIn}">
			    <tr>
			      <td><a href="/projects/show/${project.id}">${project.title}</a></td>
			      <td>${project.leader.firstName}</td>
			      <td>${project.dueDate}</td>
   			      <td>
   			      	<form action="/join/team/${project.id}" method="POST">
   			      		<button class="btn btn-success" type="submit">join team</button>
   			      	</form>
   			      </td>
			    </tr>
			  </c:forEach>
			  </tbody>
			</table>
		</div>
		<div>
			<table class="table">
			  <thead>
			    <tr>
			      <th scope="col">Project</th>
			      <th scope="col">Team Lead</th>
			      <th scope="col">Due Date</th>
			      <th scope="col">Actions</th>
			    </tr>
			  </thead>
			  <tbody>
			  <c:forEach var="project" items="${projects}">
			    <tr>
			      <td><a href="/projects/show/${project.id}">${project.title}</a></td>
			      <td>${project.leader.firstName}</td>
			      <td>${project.dueDate}</td>
   			      <td>
					<c:choose>
						<c:when test="${user.id == project.leader.id}">
							<a href="/projects/edit/${project.id}">edit</a>
						</c:when>
						<c:otherwise>
							<form action="/leave/team/${project.id}" method="POST">
								<button class="btn btn-danger" type="submit">leave team</button>
							</form>
						</c:otherwise>
					</c:choose>	
   			      </td>
			    </tr>
			    </c:forEach>
			  </tbody>
			</table>
		</div>
	</div>
</body>
</html>