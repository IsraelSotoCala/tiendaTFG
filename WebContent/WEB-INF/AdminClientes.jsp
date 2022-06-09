<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Clientes</title>

<!-- CSS only -->
<link href="./Styles/AdminClientes/AdminClientes.css" rel="stylesheet"
	type="text/css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor"
	crossorigin="anonymous">
</head>
<body>

	<div class="main">
		<div class="contenedor-opciones">

			<h4>Bienvenido administrador</h4>

			<a href="ListarClientes" class="btn btn-primary">Listar Clientes</a>

			<form method="post">
				<input type="submit" value="Cerrar Sesión" name="cerrarSesion" class="btn btn-dark">
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