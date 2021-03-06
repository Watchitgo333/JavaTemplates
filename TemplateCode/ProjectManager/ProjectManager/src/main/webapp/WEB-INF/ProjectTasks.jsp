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
<title>Project Tasks</title>
</head>
<body>
	<div class="mx-auto col-4 d-flex justify-content-start">
		<h2 class="m-4">Create a Task</h2>
		<a href="/dashboard">dashboard</a>
		<form action="/logout" method="GET">
			<button type="submit" class="btn btn-dark m-4">Logout</button>
		</form>
	</div>
	<div class="container">
		<h4>Project: ${project.title}</h4>
		<h4>Lead: ${project.leader.firstName}</h4>
		<form:form class="col-6" action="/projects/${project.id}/tasks/create" method="POST" modelAttribute="task">
			<div class="mb-3">
				<form:label path="taskName" class="form-label">Add a Task Ticket for this Team:</form:label>
				<form:errors class="text-danger" path="taskName"/>
				<form:textarea class="form-control" path="taskName"/>
			</div>
			<button class="btn btn-primary" type="submit">Create Task</button>
		</form:form>
		<div>
			<c:forEach var="task" items="${project.tasks}">
				<p>Task created by: ${task.user.firstName}</p>
				<p>at ${task.createdAt}</p>
				<p>Task name: ${task.taskName}</p>
			</c:forEach>
		</div>
	</div>
</body>
</html>