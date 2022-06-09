<%@page import="modeloTFG.ClienteVO"%>
<%@page import="modeloTFG.ProductoVO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Carrito</title>

<!-- CSS only -->
<link href="./Styles/Carrito/Carrito.css" rel="stylesheet"
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

	<div class="contenedor-carrito">
		<h2>Tu carrito</h2>
		<form action="ConfirmarCompra">
			<ul class="lista-carrito list-group">
				<%
					if (session.getAttribute("carritoVacio") != null) {
				%>
				<h2><%=session.getAttribute("carritoVacio")%></h2>
				<%
					session.setAttribute("carritoVacio", null);
				%>
				<%
					}
				%>

				<%
					if (session.getAttribute("inicioSesion") != null) {
				%>
				<h2><%=session.getAttribute("inicioSesion")%></h2>
				<%
					session.setAttribute("inicioSesion", null);
				%>
				<%
					}
				%>

				<%
					ArrayList<ProductoVO> productos;

					productos = (ArrayList<ProductoVO>) session.getAttribute("carrito");

					double total = 0;

					if (productos != null) {

						for (int i = 0; i < productos.size(); i++) {
				%>
				<li class="producto-carrito list-group-item">
					<div class="container-fluid">
						<img src="CargarImagenCarrito?prod=<%=i%>" width="200px">
						<span><b>Nombre: </b><%
							out.println(productos.get(i).getNombre());
						%></span> <span><b>Precio: </b>
						<%
							out.println(productos.get(i).getPrecio() + "€");
									total += productos.get(i).getPrecio();
						%></span>
						<span class="idProducto" style="display: none"> <%
 	out.println(productos.get(i).getId());
 %>
						</span> <img src="assets/img/borrar.png" width="25px"
							class="borrarProducto">
					</div> <%
 	}
 	} else { 
 %> <h4>El carrito está vacío</h4><% } %>
				</li>
			</ul>
			<div class="precio-confirmar">
				<div class="precio-compra">
					<h4>Precio total de la compra:</h4>
					<h4 class="precioTotal">
						<%
							out.println(total + "€");
						%>
					</h4>
				</div>

				<div class="confirmar-compra">
					<button class="btn btn-dark">
						<a href="ListaProductos">Seguir comprando</a>
					</button>

					<input type="submit" value="Realizar Compra" class="btn btn-dark">
				</div>
			</div>
		</form>
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

	<!-- JavaScript Bundle with Popper -->
	<script src="js/jquery-3.3.1.js"></script>
	<script src="js/jquery-3.3.1.min.js"></script>
	<script src="js/desplegable.js"></script>
	<script src="js/carrito.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2"
		crossorigin="anonymous"></script>

</body>
</html>