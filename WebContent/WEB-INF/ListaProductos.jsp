<%@page import="modeloTFG.ClienteVO"%>
<%@page import="modeloTFG.ProductoVO"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib prefix="et" uri="etiquetas.tld" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Lista Productos</title>
<!-- CSS only -->
<link href="./Styles/ListaProductos/ListaProductos.css" rel="stylesheet"
	type="text/css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor"
	crossorigin="anonymous">
</head>

<body>

	<header class="cabecera">
		<nav class="navegador">
			<a href="PaginaPrincipal"><img class="logo"
				src="/tiendaTFG/assets/img/Logo.png"></a> <a
				href="ListaProductos?cat=Manuales 5e">Manuales</a> <a
				href="ListaProductos?cat=Juegos de mesa">Juegos de Mesa</a> <a
				href="ListaProductos?cat=Complementos">Complementos</a> <a
				href="ListaProductos?cat=Miniaturas">Miniaturas</a>
			<div class="contenedor-perfil">
				<div class="contenido-perfil">
					<img src="CargarImagenPerfil" width="100px"
						onerror="this.onerror=null; this.src='/tiendaTFG/assets/img/perfil.png';">
					<span> Bienvenido <%
						out.print(session.getAttribute("nombre"));
					%>
					</span> <img src="/tiendaTFG/assets/img/menu.png" class="desplegar">
				</div>
				<div class="menuDesplegable">
					<%
						ClienteVO cliente;
						cliente = (ClienteVO) session.getAttribute("cliente");

						if (cliente != null) {
					%>
					<a href="EditarPerfil">Editar Perfil</a> 
					<a href="Carrito">Mi carrito</a> 
					<a href="DetallesPedido">Mis Pedidos</a>
					<%
						} else {
					%>
					<a href="LoginClientes">Iniciar sesion</a>
					<%
						}
					%>
				</div>
			</div>
		</nav>
	</header>

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
<div class="main">
		<div class="contenedor-filtros">
			<div class="titulos">
				<h4>Filtro de Búsqueda</h4>
				<h4>Búsqueda por nombre</h4>
				<h4>Filtro por categoría</h4>
			</div>
			<form action="ListaProductos" onsubmit="this.form.submit()">
			<div class="opciones-filtrado">
				<select name="Filtrar" class="form-select">
					<option value="">Sin filtro</option>
					<option value="nombre">Nombre, ascendente</option>
					<option value="nombre desc">Nombre, descendente</option>
					<option value="precio">Por precio, ascendente</option>
					<option value="precio desc">Por precio, descendente</option>
				</select>

				<input type="text" name="texto">
				<select name="Categoria">
					<option value="">Sin categoría</option>
					<option value="Manuales 5e">Manuales 5e</option>
					<option value="Complementos">Complementos</option>
					<option value="Juegos de mesa">Juegos de mesa</option>
					<option value="Miniaturas">Miniaturas</option>
				</select>
				</div>
				<div class="container-fluid">
					<input type="submit" value="Aplicar Filtro" class="btn btn-dark">

					<button class="btn btn-dark">
						<a href="ListaProductos">Eliminar Filtro</a>
					</button>
				</div>

			</form>

		</div>

		<!-- Aquí creamos la lista de articulos -->
		
		<div>
		
		<%
		if (session.getAttribute("productoAnanido") != null) {
			%>
			<h2 style="margin:20px"><%=session.getAttribute("productoAnanido")%></h2>
			<%
		session.setAttribute("productoAnanido", null);
		}
		%>

		<ul class="list-group">
			<%
				ArrayList<ProductoVO> productos;
				productos = new ArrayList<ProductoVO>();

				productos = (ArrayList<ProductoVO>) session.getAttribute("productos");

				for (int i = 0; i < productos.size(); i++) {
					if(!productos.isEmpty()) {
			%>
			<et:CatalogoProductos nombre="<%=productos.get(i).getNombre()%>" descripcion="<%=productos.get(i).getDescripcion()%>"
			precio="<%=productos.get(i).getPrecio() %>" fechaLanzamiento="<%=productos.get(i).getFechaLanzamiento() %>" posicion="<%=i%>" />
			<%
					}
				}
			%>
		</ul>
		</div>

		<div class="paginas">
			<div class="botones-paginas">
				<!-- Si estamos en la primera página no podemos ir a la anterior -->

				<%
					if (pag == 0) {
				%>
				<button disabled class="btn btn-dark">Anterior</button>
				<%
					} else {
				%>
				<button class="btn btn-dark">
					<a href="ListaProductos?pag=<%=pag - 1%>">Anterior</a>
				</button>
				<%
					}
				%>

				<!-- Numeros de páginas -->
				<%
					for (int i = 0; i < numPaginas; i++) {
				%><div class="Page navigation">
                  	<ul class="pagination">
                       	<li class="page-item">
                       		<a class="page-link" href="ListaProductos?pag=<%=i%>"> <%=i%></a>
						</li>
					</ul>
				</div>
				<%
					}
				%>

				<!-- Si estamos en la ultima pagina no podemos ir a la siguiente -->

				<%
					if (pag == numPaginas - 1) {
				%>
				<button disabled class="btn btn-dark">Siguiente</button>
				<%
					} else {
				%>
				<button class="btn btn-dark">
					<a href="ListaProductos?pag=<%=pag + 1%>">Siguiente</a>
				</button>
				<%
					}
				%>
			</div>

			<a href="PaginaPrincipal">Volver a la pagina principal</a>

		</div>
</div>
	<!-- FOOTER -->
	<footer class="w-100 py-4 flex-shrink-0">
		<div class="container py-4">
			<div class="row gy-4 gx-5">
				<div class="col-lg-4 col-md-6">
					<img src="/tiendaTFG/assets/img/Logo.png" class="logo">
					<h5>La Mazmorra del Sopas</h5>
				</div>
				<div class="col-lg-4 col-md-6">
					<h5 class="h1">Productos Oficiales</h5>
					<p class="small">Tu tienda de productos de D&D en España.</p>
					<p class="small mb-0">&copy; Copyrights.</p>
				</div>
				<div class="col-lg-2 col-md-6">
					<h5 class="mb-3">Redes Sociales</h5>
					<ul class="list-unstyled">
						<li><a href="#">Facebook</a></li>
						<li><a href="#">Twitter</a></li>
						<li><a href="#">Instagram</a></li>
						<li><a href="#">Google</a></li>
					</ul>
				</div>
				<div class="col-lg-2 col-md-6">
					<h5 class="mb-3">Enlaces rápidos</h5>
					<ul class="list-unstyled">
						<li><a href="#">Wizards of the Coast</a></li>
						<li><a href="#">Acerca de </a></li>
						<li><a href="#">D&D</a></li>
						<li><a href="Contactar">Ayuda y soporte, contáctanos</a></li>
					</ul>
				</div>
			</div>
		</div>
	</footer>


	<script src="js/jquery-3.3.1.js"></script>
	<script src="js/jquery-3.3.1.min.js"></script>
	<script src="js/desplegable.js"></script>
	<!-- JavaScript Bundle with Popper -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2"
		crossorigin="anonymous"></script>


</body>

</html>