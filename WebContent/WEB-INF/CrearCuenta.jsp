<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Crear Cuenta</title>

<!-- CSS only -->
<link href="./Styles/CrearCuenta/CrearCuenta.css" rel="stylesheet"
	type="text/css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor"
	crossorigin="anonymous">
</head>
<body>

	<div class="main">
		<div class="contenedor-crear-cuenta">

			<form method="post" enctype="multipart/form-data">

				<h2>Creación de cuenta</h2>


				<%
					if (session.getAttribute("creada") != null) {
				%>
				<h2><%=session.getAttribute("creada")%></h2>
				<%
					session.setAttribute("creada", null);
				%>
				<%
					}
				%>

				<h4>Nombre</h4>
				<input type="text" name="nombre" maxlength="45">

				<h4>Dirección</h4>
				<input type="text" name="direccion" maxlength="45">

				<h4>Localidad</h4>
				<input type="text" name="localidad" maxlength="45">

				<h4>Provincia</h4>
				<input type="text" name="provincia" maxlength="45">

				<h4>Código Postal</h4>
				<input type="text" name="codigopostal" maxlength="5">

				<h4>Correo - Se enviará un correo a esta dirección para
					verificar tu cuenta</h4>
				<input type="email" name="correo" required="required" maxlength="45">

				<h4>Contraseña</h4>
				<input type="password" name="contrasenia" required="required" maxlength="256">

				<h4>Teléfono</h4>
				<input type="text" name="telefono" maxlength="9">

				<h4>Imagen de perfil</h4>
				<input type="file" name="imagen" accept="image/*"> <input
					type="submit" value="Crear Cuenta" class="btn btn-dark"> <a
					href="LoginClientes">Volver</a>

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