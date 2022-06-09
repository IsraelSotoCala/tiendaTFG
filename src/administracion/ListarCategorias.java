package administracion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.IntPredicate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modeloTFG.AdministradorVO;
import modeloTFG.CategoriaDAO;
import modeloTFG.CategoriaVO;
import modeloTFG.ProductoDAO;

/**
 * Servlet implementation class ListarCategorias
 */
@WebServlet("/ListarCategorias")
public class ListarCategorias extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession sesion;

		sesion = request.getSession();

		AdministradorVO admin;

		admin = (AdministradorVO) sesion.getAttribute("admin");
		
		if(admin !=null && admin.getRol().equals("productos")) {
			
			CategoriaDAO cDAO;
			
			cDAO = new CategoriaDAO();
			
			ArrayList<CategoriaVO> categorias;
			
			categorias = new ArrayList<CategoriaVO>();
			
			int pagPredeterminada;

			pagPredeterminada = 0;

			int categoriasPag;

			int numPaginas;

			int totalCategorias;
			
			// Filtros de búsqueda

			String filtro;

			filtro = request.getParameter("Filtrar");

			if (filtro == null || filtro.equals("")) {
				filtro = "id";
			}

			// Filtro de búsqueda por texto

			String busqueda;

			String sql;

			busqueda = request.getParameter("texto");

			if (busqueda == null) {
				sql = "ILIKE '%%'";
			} else {
				sql = "ILIKE '" + busqueda + "%'";
			}
			
			totalCategorias = cDAO.resultados(sql);
			
			if(totalCategorias % 5 == 0) {
				numPaginas = totalCategorias / 5;
			} else {
				numPaginas = (totalCategorias / 5) + 1;
			}
			
			sesion.setAttribute("numPaginas", numPaginas);
			
			// Controlamos que si el nº de pag es nulo, menor a 0 o mayor que el nº de pag
			// totales
			// El offset sea desde el principio
			if (request.getParameter("pag") == null || Integer.valueOf(request.getParameter("pag")) < 0
					|| Integer.valueOf(request.getParameter("pag")) > numPaginas - 1) {
				categoriasPag = pagPredeterminada;
			} else {
				categoriasPag = Integer.parseInt(request.getParameter("pag")) * 5;
			}
			
			//Hacemos la consulta 
			categorias = cDAO.consultarCategorias(filtro, categoriasPag, sql);
			
			sesion.setAttribute("categorias", categorias);
			
			request.getRequestDispatcher("WEB-INF/ListarCategorias.jsp").forward(request, response);
		} else {
			response.sendRedirect("LoginEmpleados");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
