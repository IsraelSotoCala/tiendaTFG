<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Contactar</title>

<!-- CSS only -->
<link href="./Styles/Contactar/Contactar.css" rel="stylesheet"
	type="text/css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor"
	crossorigin="anonymous">
</head>
<body>

	<div class="main">
		<div class="contenedor-contactar">
			<form method="post">
				<h2>Contactar con el soporte</h2>
				<%
					if (session.getAttribute("soporte") != null) {
				%>
				<h2><%=session.getAttribute("soporte")%></h2>
				<%
					session.setAttribute("soporte", null);
				%>
				<%
					}
				%>
				
				<h4>Introduzca el correo al que quiere que le contactemos</h4>
				<input type="email" name="correo" required="required" maxlength="45"> 
				
				<h4>Introduzca el tipo problema que tiene</h4>
				<input type="text" name="problema" required maxlength="45">
				
				<h4>Introduzca una descripción del problema</h4>
				<textarea rows="5" cols="20" required maxlength="250" name="descripcion"></textarea>
				
				<input type="submit" class="btn btn-dark" value="Enviar">
				
				<a href="PaginaPrincipal">Volver</a>
			</form>
		</div>
	</div>


	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2"
		crossorigin="anonymous"></script>

</body>
</html>