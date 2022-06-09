<%@page import="modeloTFG.ClienteVO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="modeloTFG.ProductoVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Detalles Producto</title>

<!-- CSS only -->
<link href="./Styles/DetallesProducto/DetallesProducto.css"
	rel="stylesheet" type="text/css">
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
					<a href="EditarPerfil">Editar Perfil</a> <a href="Carrito">Mi
						carrito</a> <a href="DetallesPedido">Mis Pedidos</a>
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
		ProductoVO producto;

		producto = (ProductoVO) session.getAttribute("producto");
	%>

	<div class="detalles-producto">
		<div class="imagen-datos">
			<div class="contenedor-imagen-producto">
				<img src="CargarImagenProducto" class="imagen-producto">
			</div>
			<div class="datos-producto">
				<h2>
					<%
						out.println(producto.getNombre());
					%>
				</h2>
				<span> <b>Precio:</b> <%
					out.println(producto.getPrecio() + "$");
				%>
				</span> <span> <b>Descripción:</b> <%
 	out.println(producto.getDescripcion());
 %>
				</span> <span> <b>Fecha de lanzamiento:</b> <%
 	out.println(producto.getFechaLanzamiento());
 %>
				</span>
				
								
						<div class="info-producto">

			<h2>Acerca de este producto</h2>

			<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed
				do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut
				enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi
				ut aliquip ex ea commodo consequat. Duis aute irure dolor in
				reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla
				pariatur. Excepteur sint occaecat cupidatat non proident, sunt in
				culpa qui officia deserunt mollit anim id est laborum.</p>

		</div>

				<div class="anadir-volver">
					<form action="AnadirAlCarrito">
						<input type="submit" value="Añadir al carrito"
							class="btn btn-dark">
					</form>

					<button class="btn btn-dark">
						<a href="ListaProductos">Volver</a>
					</button>
				</div>

			</div>
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
						<li><a href="#">FAQ</a></li>
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