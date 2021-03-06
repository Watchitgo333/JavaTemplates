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
<title>Edit Project</title>
</head>
<body>
	<div class="mx-auto col-4 d-flex justify-content-start">
		<h2 class="m-4">Edit Project</h2>
		<a href="/dashboard">dashboard</a>
		<form action="/logout" method="GET">
			<button type="submit" class="btn btn-dark m-4">Logout</button>
		</form>
	</div>
	<div class="container">
		<form:form class="col-6" action="/projects/update/${project.id}" method="POST" modelAttribute="project">
			<input type="hidden" name="_method" value="PUT"/>
			<div class="mb-3">
				<form:label path="title" class="form-label">Project Title:</form:label>
				<form:errors class="text-danger" path="title"/>
				<form:input class="form-control" path="title"/>
			</div>
			<div class="mb-3">
				<form:label path="description" class="form-label">Project Description:</form:label>
				<form:errors class="text-danger" path="description"/>
				<form:textarea class="form-control" path="description"/>
			</div>
			<div class="mb-3">
				<form:label path="dueDate" class="form-label">Project Due Date:</form:label>
				<form:errors class="text-danger" path="dueDate"/>
				<form:input class="form-conrol" type="date" path="dueDate"/>
			</div>
			<button class="btn btn-primary" type="submit">Update</button>
		</form:form>
	</div>
</body>
</html>