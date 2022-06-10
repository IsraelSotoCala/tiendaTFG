package paginas;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import miBibliotecaGeneral.CorreoSoporte;
import modeloTFG.ClienteVO;

/**
 * Servlet implementation class Contactar
 */
@WebServlet("/Contactar")
public class Contactar extends HttpServlet {
	private static final long serialVersionUID = 1L;

	HttpSession sesion;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//Variables
		
		sesion = request.getSession();
		
		ClienteVO cliente;
		
		cliente = (ClienteVO) sesion.getAttribute("cliente");
		
		if(cliente != null) {
			request.getRequestDispatcher("WEB-INF/Contactar.jsp").forward(request, response);
		} else {
			sesion.setAttribute("inicioSesion", "Necesita iniciar sesion para acceder");
			request.getRequestDispatcher("WEB-INF/PaginaPrincipal.jsp").forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//Variables
		
		String correo;
		
		String problema;
		
		String descripcion;
		
		CorreoSoporte soporte;
		
		correo = request.getParameter("correo");
		
		problema = request.getParameter("problema");
		
		descripcion = request.getParameter("descripcion");
		
		soporte = new CorreoSoporte();
		
		//Comprobamos que se envie el correo 
		
		if(soporte.correoSoporte(correo, problema, descripcion) == true) {
			sesion.setAttribute("soporte", "Mensaje enviado con exito");
			response.sendRedirect("Contactar");
		} else {
			sesion.setAttribute("soporte", "Fallo al enviar al correo");
			response.sendRedirect("Contactar");
		};
	}

}
