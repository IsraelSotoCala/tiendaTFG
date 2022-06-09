package paginas;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import miBibliotecaGeneral.Encriptado;
import modeloTFG.AdministradorDAO;
import modeloTFG.AdministradorVO;

/**
 * Servlet implementation class LoginEmpleados
 */
@WebServlet("/LoginEmpleados")
public class LoginEmpleados extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("WEB-INF/LoginEmpleados.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// Variables

		HttpSession sesion;

		Encriptado encrypt;

		encrypt = new Encriptado();

		sesion = request.getSession();

		String mail;

		String contrasenia;

		mail = request.getParameter("mail");

		contrasenia = encrypt.generar(request.getParameter("contrasenia"));

		AdministradorVO admin;

		AdministradorDAO ad;

		ad = new AdministradorDAO();

		// Cargamos el admin por correo

		admin = ad.consultar(mail);

		// Si el admin no existe le mostramos error
		if (admin == null) {
			response.sendRedirect("LoginEmpleados");
			sesion.setAttribute("login", "Correo o contraseña incorrectos");
		} else {
			// Cliente
			if (admin.getRol().equals("clientes") && mail.equals(admin.getEmail())
					&& contrasenia.equals(admin.getPassword())) {
				sesion.setAttribute("admin", admin);
				response.sendRedirect("AdminClientes");
			} else {
				// Productos
				if (admin.getRol().equals("productos") && mail.equals(admin.getEmail())
						&& contrasenia.equals(admin.getPassword())) {
					sesion.setAttribute("admin", admin);
					response.sendRedirect("AdminProductos");
				} else {
					// Si el correo o la contraseña es incorrecta se le redirige a la pagina de
					// login con un error
					sesion.setAttribute("login", "Correo o contraseña incorrectos");
					response.sendRedirect("LoginEmpleados");
				}
			}
		}
	}

}
