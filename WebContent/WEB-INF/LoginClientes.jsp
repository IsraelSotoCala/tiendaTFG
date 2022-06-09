<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Mazmorra del sopas</title>

<!-- CSS only -->
<link href="./Styles/LoginClientes/LoginClientes.css" rel="stylesheet"
	type="text/css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor"
	crossorigin="anonymous">
</head>
<body>

	<div class="main">
		<div class="contenedor-login">
			<form method="post">
	
				<h2>Bienvenido a la mazmorra del Sopas</h2>
	
				<h4>Inicio Sesión</h4>
	
				<label for="mail">Introduzca su email</label> 
				<input type="text" name="mail">
				 
				<label for="contrasenia">Introduzca su contraseña</label>
				<input type="password" name="contrasenia">
					
				<input type="submit" value="Iniciar Sesion" class="btn btn-dark">
	
				<%
					if (session.getAttribute("login") != null) {
				%>
				<span><%=session.getAttribute("login")%></span>
				<%
					session.setAttribute("login", null);
				%>
				<%
					}
				%>
	
	
				<a href="CrearCuenta">¿No tienes una cuenta? Crea una aquí</a>
	
			</form>
		</div>
	</div>

	<!-- JavaScript Bundle with Popper -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2"
		crossorigin="anonymous"></script>

</body>
</html>