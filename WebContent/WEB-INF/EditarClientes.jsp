<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Editar Clientes</title>
<!-- CSS only -->
<link href="./Styles/EditarClientes/EditarClientes.css" rel="stylesheet"
	type="text/css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor"
	crossorigin="anonymous">

</head>
<body>

<div class="main">
		<div class="contenedor-editar-clientes">


			<form method="post" enctype="multipart/form-data">

				<h2>Editar Cliente</h2>
				
				<%
					if (session.getAttribute("clienteEditado") != null) {
				%>
				<h2><%=session.getAttribute("clienteEditado")%></h2>
				<%
					session.setAttribute("clienteEditado", null);
				%>
				<%
					}
				%>

				<h4>Nombre</h4>
				<input type="text" name="nombre" value="<%=session.getAttribute("nombre")%>" maxlength="45">

				<h4>Dirección</h4>
				<input type="text" name="direccion" value="<%=session.getAttribute("direccion")%>" maxlength="45">

				<h4>Localidad</h4>
				<input type="text" name="localidad" value="<%=session.getAttribute("localidad")%>" maxlength="45">

				<h4>Provincia</h4>
				<input type="text" name="provincia" value="<%=session.getAttribute("provincia")%>" maxlength="45">

				<h4>Código Postal</h4>
				<input type="text" name="codigopostal" value="<%=session.getAttribute("codigoPostal")%>" maxlength="5">

				<h4>Teléfono</h4>
				<input type="text" name="telefono" value="<%=session.getAttribute("telefono")%>" maxlength="9">

				<h4>Imagen de perfil</h4>
				<input type="file" name="imagen" accept="image/*">
				
				<input type="submit" value="Editar perfil" class="btn btn-dark"> 
				
				<a href="ListarClientes">Volver</a>

			</form>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2"
		crossorigin="anonymous"></script>

</body>
</html>