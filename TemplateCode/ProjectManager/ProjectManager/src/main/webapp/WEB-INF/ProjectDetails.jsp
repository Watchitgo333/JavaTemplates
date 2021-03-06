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
<title>Project Details</title>
</head>
<body>
	<div class="mx-auto col-4 d-flex justify-content-start">
		<h2 class="m-4">Project Details</h2>
		<a href="/dashboard">dashboard</a>
		<form action="/logout" method="GET">
			<button type="submit" class="btn btn-dark m-4">Logout</button>
		</form>
	</div>
	<div class="container">
		<div>
			<p>Project: ${project.title}</p>
			<p>Team Lead: ${project.leader.firstName}
			<p>Description: ${project.description}</p>
			<p>Due Date: ${project.dueDate}</p>
		</div>
		<c:forEach var="user" items="${users}">
			<c:if test="${user.id == userInSesh.id}">
				<a href="/projects/${project.id}/tasks">tasks</a>
			</c:if>
		</c:forEach>
			<c:if test="${project.leader.id == userInSesh.id}">
				<form action="/projects/delete/${project.id}" method="POST">
				<input type="hidden" name="_method" value="DELETE"/>
					<button type="submit" class="btn btn-danger">Delete Project</button>
				</form>
			</c:if>
	</div>
</body>
</html>