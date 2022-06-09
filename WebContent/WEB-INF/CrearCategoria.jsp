<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Crear Categoria</title>

<!-- CSS only -->
<link href="./Styles/CrearCategoria/CrearCategoria.css" rel="stylesheet"
	type="text/css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor"
	crossorigin="anonymous">
</head>
<body>

	<div class="main">
		<div class="contenedor-crear-categoria">
			<form method="post">

				<h2>Creación de categoría</h2>


				<%
					if (session.getAttribute("categoriaCreada") != null) {
				%>
				<h2><%=session.getAttribute("categoriaCreada")%></h2>
				<%
					session.setAttribute("categoriaCreada", null);
				%>
				<%
					}
				%>

				<h4>Nombre</h4>
				<input type="text" name="nombre" maxlength="45">

				<h4>Descripción</h4>
				<input type="text" name="descripcion" maxlength="100"> 
				<input type="submit"
					value="Crear Categoría" class="btn btn-dark"> <a
					href="AdminProductos">Volver</a>

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