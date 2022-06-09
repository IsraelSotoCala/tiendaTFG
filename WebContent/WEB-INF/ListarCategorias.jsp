<%@page import="modeloTFG.CategoriaVO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Listar Categorias</title>

<!-- CSS only -->
<link href="./Styles/ListarCategorias/ListarCategorias.css"
	rel="stylesheet" type="text/css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor"
	crossorigin="anonymous">
</head>
<body>

	<%
		int pag;

		int numPaginas;
		numPaginas = (int) session.getAttribute("numPaginas");

		//Controlamos que si se introduce en la cabecera algun numero que no sea viable, volvemos a la pagina inicial
		if (request.getParameter("pag") == null || Integer.valueOf(request.getParameter("pag")) < 0
				|| Integer.valueOf(request.getParameter("pag")) > numPaginas) {
			pag = 0;
		} else {
			pag = Integer.parseInt(request.getParameter("pag"));
		}
	%>

	<!-- Filtros de búsqueda -->
	<div class="contenedor-filtros">
		<h4>Filtro de Búsqueda</h4>
		<form action="ListarCategorias" onsubmit="this.form.submit()">
			<select name="Filtrar">
				<option value="">Sin filtro</option>
				<option value="nombre">Nombre, ascendente</option>
				<option value="nombre desc">Nombre, descendente</option>
			</select>
			<h4>Búsqueda por nombre</h4>
			<input type="text" name="texto"> <input type="submit"
				value="Aplicar Filtro" class="btn btn-dark">
		</form>

		<button class="btn btn-dark">
			<a href="ListarCategorias">Eliminar Filtro</a>
		</button>

	</div>

	<!-- Aquí creamos la lista de articulos -->

	<%
		if (session.getAttribute("categoriaEditada") != null) {
	%>
	<h2><%=session.getAttribute("categoriaEditada")%></h2>
	<%
		session.setAttribute("categoriaEditada", null);
	%>
	<%
		}
	%>

	<%
		if (session.getAttribute("categoriaBorrada") != null) {
	%>
	<h2><%=session.getAttribute("categoriaBorrada")%></h2>
	<%
		session.setAttribute("categoriaBorrada", null);
	%>
	<%
		}
	%>

	<ul class="list-group">
		<%
			ArrayList<CategoriaVO> categorias;
			categorias = new ArrayList<CategoriaVO>();

			categorias = (ArrayList<CategoriaVO>) session.getAttribute("categorias");

			for (int i = 0; i < categorias.size(); i++) {
		%>

		<li class="list-group-item">
			<div class="container-fluid">
				<span><%
					out.println(categorias.get(i).getNombre());
				%></span>
				<span><%
					out.println(categorias.get(i).getDescripcion());
				%></span>
				<div class="opciones">
					<a href="EditarCategoria?cat=<%=i%>">Editar</a> <a
						href="BorrarCategoria?cat=<%=i%>">Borrar</a>
				</div>
			</div>
		</li>

		<%
			}
		%>
	</ul>

	<div class="paginas">
		<div class="botones-paginas">

			<!-- Si estamos en la primera página no podemos ir a la anterior -->

			<%
				if (pag == 0) {
			%>
			<button disabled>Anterior</button>
			<%
				} else {
			%>
			<button>
				<a href="ListarCategorias?cat=<%=pag - 1%>">Anterior</a>
			</button>
			<%
				}
			%>

			<!-- Numeros de páginas -->
			<%
				for (int i = 0; i < numPaginas; i++) {
			%><a href="ListarCategorias?cat=<%=i%>"><%=i%></a>
			<%
				}
			%>

			<!-- Si estamos en la ultima pagina no podemos ir a la siguiente -->

			<%
				if (pag == numPaginas - 1) {
			%>
			<button disabled>Siguiente</button>
			<%
				} else {
			%>
			<button>
				<a href="ListarCategorias?cat=<%=pag + 1%>">Siguiente</a>
			</button>
			<%
				}
			%>

		</div>

		<a href="AdminProductos">Volver a la pagina de empleados</a>

	</div>

	<!-- JavaScript Bundle with Popper -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2"
		crossorigin="anonymous"></script>

</body>
</html>