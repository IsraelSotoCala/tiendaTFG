package administracion;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modeloTFG.AdministradorVO;
import modeloTFG.ClienteDAO;
import modeloTFG.ClienteVO;

/**
 * Servlet implementation class ListarClientes
 */
@WebServlet("/ListarClientes")
public class ListarClientes extends HttpServlet {
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
		
		//Solo se puede acceder a la página si eres admin y del rol de clientes
		if(admin!=null && admin.getRol().equals("clientes")) {
			
			ClienteDAO cDAO;
			
			cDAO = new ClienteDAO();
			
			ArrayList<ClienteVO> clientes;
			
			clientes = new ArrayList<ClienteVO>();
			
			int pagPredeterminada;

			pagPredeterminada = 0;

			int clientesPag;

			int numPaginas;

			int totalClientes;
			
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
			
			totalClientes = cDAO.resultados(sql);
			
			if(totalClientes % 5 == 0) {
				numPaginas = totalClientes / 5;
			} else {
				numPaginas = (totalClientes / 5) + 1;
			}
			
			sesion.setAttribute("numPaginas", numPaginas);
			
			// Controlamos que si el nº de pag es nulo, menor a 0 o mayor que el nº de pag
			// totales
			// El offset sea desde el principio
			if (request.getParameter("pag") == null || Integer.valueOf(request.getParameter("pag")) < 0
					|| Integer.valueOf(request.getParameter("pag")) > numPaginas - 1) {
				clientesPag = pagPredeterminada;
			} else {
				clientesPag = Integer.parseInt(request.getParameter("pag")) * 5;
			}
			
			//Hacemos la consulta 
			clientes = cDAO.consultarClientes(filtro, clientesPag, sql);
			
			sesion.setAttribute("clientes", clientes);
			
			request.getRequestDispatcher("WEB-INF/ListarClientes.jsp").forward(request, response);
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
