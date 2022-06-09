package administracion;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modeloTFG.AdministradorVO;

/**
 * Servlet implementation class AdminProductos
 */
@WebServlet("/AdminProductos")
public class AdminProductos extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	HttpSession sesion;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//Comprobamos que se ha conectado un administrador y si no lo enviamos de vuelta al login
		
		sesion = request.getSession();
		
		AdministradorVO admin;
		
		admin = (AdministradorVO) sesion.getAttribute("admin");
		
		//Solo se puede acceder a la p·gina si eres admin y del rol de productos
		if(admin!=null && admin.getRol().equals("productos")) {
			request.getRequestDispatcher("WEB-INF/AdminProductos.jsp").forward(request, response);
		} else {
			response.sendRedirect("LoginEmpleados");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		sesion = request.getSession();

		// Con esto cerramos la sesi√≥n y enviamos de vuelta al login
		request.getSession().invalidate();
		response.sendRedirect("LoginEmpleados");
	}

}
