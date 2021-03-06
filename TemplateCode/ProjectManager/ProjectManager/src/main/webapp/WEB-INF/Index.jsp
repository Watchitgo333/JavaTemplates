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
<title>Login & Registration</title>
</head>
<body>
	<div class="mx-auto row col-4">
		<h1>Welcome!</h1>
		<h3>Become a part of the community and contribute to projects today!</h3>
	</div>
	<div class="container d-flex justify-content-evenly">
		<div class="row">
			<h3>Register</h3>
			<form:form action="/register" method="POST" modelAttribute="newUser">
				<div class="mb-3">
					<form:label path="firstName" class="form-label">First Name:</form:label>
					<form:errors class="text-danger" path="firstName"/>
					<form:input path="firstName" class="form-control"/>
				</div>
				<div class="mb-3">
					<form:label path="lastName" class="form-label">Last Name:</form:label>
					<form:errors class="text-danger" path="lastName"/>
					<form:input path="lastName" class="form-control"/>
				</div>
				<div class="mb-3">
					<form:label path="email" class="form-label">Email:</form:label>
					<form:errors class="text-danger" path="email"/>
					<form:input path="email" class="form-control"/>
				</div>
				<div class="mb-3">
					<form:label path="password" class="form-label">Password:</form:label>
					<form:errors class="text-danger" path="password"/>
					<form:input path="password" type="password" class="form-control"/>
				</div>
				<div class="mb-3">
					<form:label path="confirm" class="form-label">Confirm Password:</form:label>
					<form:errors class="text-danger" path="confirm"/>
					<form:input path="confirm" type="password" class="form-control"/>
				</div>
				<button class="btn btn-primary" type="submit">Register</button>
			</form:form>
		</div>
		<div class="row">
			<h3>Login</h3>
			<form:form action="/login" method="POST" modelAttribute="newLogin">
				<div class="mb-3">
					<form:label path="email" class="form-label">Email:</form:label>
					<form:errors class="text-danger" path="email"/>
					<form:input path="email" class="form-control"/>
				</div>
				<div class="mb-3">
					<form:label path="password" class="form-label">Password:</form:label>
					<form:errors class="text-danger" path="password"/>
					<form:input path="password" type="password" class="form-control"/>
				</div>
				<button class="btn btn-primary" type="submit">Login</button>
			</form:form>
		</div>
	</div>
</body>
</html>