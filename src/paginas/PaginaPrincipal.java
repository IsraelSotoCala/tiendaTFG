package paginas;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modeloTFG.ClienteVO;
import utiles.Cookies;

/**
 * Servlet implementation class PaginaPrincipal
 */
@WebServlet("/PaginaPrincipal")
public class PaginaPrincipal extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	HttpSession sesion;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		sesion = request.getSession();
		
		ClienteVO cliente;
		
		cliente = (ClienteVO) sesion.getAttribute("cliente");
		
		//Mostramos el nombre del cliente
		if(cliente!=null) {
			if(Cookies.getCookie("nombreUsuario", request) != null) {
				sesion.setAttribute("nombre", Cookies.getCookie("nombreUsuario", request).getValue());
			} else {
				sesion.setAttribute("nombre", "a nuestra tienda!");
			}
		} else {
			sesion.setAttribute("nombre", "a nuestra tienda!");
		}
		
		request.getRequestDispatcher("WEB-INF/PaginaPrincipal.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		sesion = request.getSession();

		//Con esto cerramos la sesión y enviamos de vuelta al login
		request.getSession().invalidate();
		response.sendRedirect("LoginClientes");
	}

}
