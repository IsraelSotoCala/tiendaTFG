<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Crear Productos</title>

<!-- CSS only -->
<link href="./Styles/CrearProductos/CrearProductos.css" rel="stylesheet"
	type="text/css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor"
	crossorigin="anonymous">
</head>
<body>

	<div class="main">
		<div class="contenedor-crear-productos">

			<form method="post" enctype="multipart/form-data">

				<h2>Creación de productos</h2>


				<%
					if (session.getAttribute("productoCreado") != null) {
				%>
				<h2><%=session.getAttribute("productoCreado")%></h2>
				<%
					session.setAttribute("productoCreado", null);
				%>
				<%
					}
				%>

				<h4>Nombre</h4>
				<input type="text" name="nombre" maxlength="100">

				<h4>Descripción</h4>
				<input type="text" name="descripcion" maxlength="250">

				<h4>Fecha de lanzamiento</h4>
				<input type="date" name="fechaLanzamiento" required>

				<h4>Precio</h4>
				<input type="text" name="precio" value="0">

				<h4>Categoria</h4>
				<input type="number" name="categoria" required>

				<h4>Imagen</h4>
				<input type="file" name="imagen" accept="image/*"> <input
					type="submit" value="Crear Producto" class="btn btn-dark">

				<a href="AdminProductos">Volver</a>

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