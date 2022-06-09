<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Borrar Clientes</title>

<!-- CSS only -->
<link href="./Styles/BorrarClientes/BorrarClientes.css" rel="stylesheet"
	type="text/css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor"
	crossorigin="anonymous">
</head>
<body>

	<div class="main">
		<div class="contenedor-borrar-clientes">

			<form method="post">

				<h2>Borrado de clientes</h2>


				<%
					if (session.getAttribute("clienteBorrado") != null) {
				%>
				<h2><%=session.getAttribute("clienteBorrado")%></h2>
				<%
					session.setAttribute("clienteBorrado", null);
				%>
				<%
					}
				%>

				<input type="submit" value="Borrar Cliente" class="btn btn-dark">

				<a href="AdminClientes">Volver</a>

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