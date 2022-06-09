package paginas;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modeloTFG.ProductoDAO;
import modeloTFG.ProductoVO;

/**
 * Servlet implementation class ListaArticulos
 */
@WebServlet("/ListaProductos")
public class ListaProductos extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession sesion;
		
		sesion = request.getSession();
		
		ProductoDAO pDAO;
		
		pDAO = new ProductoDAO();
		
		ArrayList<ProductoVO> productos;
		
		productos = new ArrayList<ProductoVO>();
		
		int pagPredeterminada;
		
		pagPredeterminada = 0;
		
		int productosPag;
		
		int numPaginas;
		
		int totalProductos;
		
		// Filtros de búsqueda
		
		String filtro;
		
		filtro = request.getParameter("Filtrar");
		
		if(filtro == null || filtro.equals("")) {
			filtro = "id";
		}
		
		// Filtro de búsqueda por texto
		
		String busqueda;
		
		String sql;
		
		busqueda = request.getParameter("texto");
		
		if(busqueda == null) {
			sql = "ILIKE '%%'";
		} else {
			sql = "ILIKE '" + busqueda + "%'";
		}
		
		//Filtro de categoria
		
		String filtroCategoria;
		
		filtroCategoria = request.getParameter("Categoria");
		
		if(filtroCategoria == null) {
			filtroCategoria = request.getParameter("cat");
		}
		
		//Si el filtro de familia es nulo hacemos lo siguiente
		if(filtroCategoria == null || filtroCategoria.equals("")) {
			//Cargamos el total de productos para calcular el nº de paginas
			totalProductos = pDAO.resultados(sql);
			//Calculamos, si el resto es 0 el nº de pag es exacto, si no, le sumamos 1 pag
			if (totalProductos % 5 == 0) {
				numPaginas = totalProductos / 5;
			} else {
				numPaginas = (totalProductos / 5) + 1;
			}
			
			sesion.setAttribute("numPaginas", numPaginas);
			
			//Controlamos que si el nº de pag es nulo, menor a 0 o mayor que el nº de pag totales
			//El offset sea desde el principio
			if(request.getParameter("pag") == null || Integer.valueOf(request.getParameter("pag")) <0
					|| Integer.valueOf(request.getParameter("pag")) > numPaginas - 1) {
				productosPag = pagPredeterminada;
			} else {
				productosPag = Integer.parseInt(request.getParameter("pag")) * 5;
			}
			
			//Hacemos la consulta de los articulos sin el filtro de categoria
			productos = pDAO.consultarProductos(filtro, productosPag, sql);
		} else {
			//Ahora para cuando haya filtro de categoria hacemos lo mismo
			//Calculamos el total de articulos pero los que pertenezcan a la categoria
			totalProductos = pDAO.resultadosProductosCategoria(filtroCategoria,sql);
			//Calculamos el nº de páginas
			if(totalProductos % 5 == 0) {
				numPaginas = totalProductos / 5;
			} else {
				numPaginas = (totalProductos / 5) + 1;
			}
			
			sesion.setAttribute("numPaginas", numPaginas);
			
			//Controlamos el offset
			if(request.getParameter("pag") == null || Integer.valueOf(request.getParameter("pag")) <0
					|| Integer.valueOf(request.getParameter("pag")) > numPaginas - 1) {
				productosPag = pagPredeterminada;
			} else {
				productosPag = Integer.parseInt(request.getParameter("pag")) * 5;
			}
			
			//Hacemos la consulta de los articulos con el filtro de familia
			productos = pDAO.consultarCategoriaProductos(filtroCategoria, sql, filtro, productosPag);
			
		}
		
		sesion.setAttribute("productos", productos);
		
		request.getRequestDispatcher("WEB-INF/ListaProductos.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
