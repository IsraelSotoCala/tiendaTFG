<%@page import="modeloTFG.ClienteVO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Listar Clientes</title>

<!-- CSS only -->
<link href="./Styles/ListarClientes/ListarClientes.css"
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
		<form action="ListarClientes" onsubmit="this.form.submit()">
			<select name="Filtrar">
				<option value="">Sin filtro</option>
				<option value="nombre">Nombre, ascendente</option>
				<option value="nombre desc">Nombre, descendente</option>
				<option value="correo">Correo, ascendente</option>
				<option value="correo desc">Correo, descendente</option>
			</select>
			<h4>Búsqueda por nombre</h4>
			<input type="text" name="texto">
			<input type="submit" value="Aplicar Filtro" class="btn btn-dark">
		</form>
	
		<button class="btn btn-dark">
			<a href="ListarClientes">Eliminar Filtro</a>
		</button>
	</div>
		<!-- Aquí creamos la lista de articulos -->

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
	
	<ul class="list-group">
	
		<%
		
			ArrayList<ClienteVO> clientes;
			clientes = new ArrayList<ClienteVO>();
			
			clientes = (ArrayList<ClienteVO>) session.getAttribute("clientes");
			
			for (int i = 0; i < clientes.size(); i++) {
		
		%>
		
		<li class="list-group-item">
		
			<img src="CargarImagenClientes?cli=<%=i%>" width="100px">
			
			<div class="container-fluid">
				
				<span>Nombre: <%
					out.println(clientes.get(i).getNombre());
				%></span> <span>Dirección: <%
					out.println(clientes.get(i).getDireccion());
				%></span> <span>Localidad: <%
					out.println(clientes.get(i).getLocalidad());
				%></span> <span>Provincia: <%
					out.println(clientes.get(i).getProvincia());
				%></span> <span>Código Postal: <%
					out.println(clientes.get(i).getCodigoPostal());
				%></span> <span>Correo: <%
					out.println(clientes.get(i).getCorreo());
				%></span> <span>Teléfono: <%
					out.println(clientes.get(i).getTelefono());
				%></span>
				<div class="opciones"> 
					<a href="EditarClientes?cli=<%=i%>">Editar</a>
					<a href="BorrarClientes?cli=<%=i %>">Borrar</a>
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
				<a href="ListarClientes?pag=<%=pag - 1%>">Anterior</a>
			</button>
			<%
				}
			%>
		
			<!-- Numeros de páginas -->
			<%
				for (int i = 0; i < numPaginas; i++) {
			%><a href="ListarClientes?pag=<%=i%>"><%=i%></a>
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
				<a href="ListarClientes?pag=<%=pag + 1%>">Siguiente</a>
			</button>
			<%
				}
			%>
		
			<a href="AdminClientes">Volver a la pagina de empleados</a>
	
	</div>
	
			<!-- JavaScript Bundle with Popper -->
		<script
			src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js"
			integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2"
			crossorigin="anonymous"></script>

</body>
</html>