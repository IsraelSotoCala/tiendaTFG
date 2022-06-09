package paginas;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import miBibliotecaGeneral.Encriptado;
import modeloTFG.ClienteDAO;
import modeloTFG.ClienteVO;

/**
 * Servlet implementation class LoginClientes
 */
@WebServlet("/LoginClientes")
public class LoginClientes extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("WEB-INF/LoginClientes.jsp").forward(request, response);
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

		ClienteVO cliente;

		ClienteDAO clientes;
		
		Cookie ck;

		clientes = new ClienteDAO();

		// Cargamos el usuario

		cliente = clientes.consultar(mail);

		// Inicio de sesión
		//Si el usuario no existe mostramos error
		if (cliente == null) {
			response.sendRedirect("LoginClientes");
			sesion.setAttribute("login", "Usuario o contraseña incorrectos");
		} else {
			// Si es la primera veez que se logea luego de registrarse, lo enviamos a
			// verificar el código
			if (cliente.getPrimeraVez() == true && mail.equals(cliente.getCorreo())
					&& contrasenia.equals(cliente.getPassword())) {
				sesion.setAttribute("cliente", cliente);
				response.sendRedirect("PrimerLogin");
			} else {
				// Si no es la primera vez que se logea, lo mandamos a la página principal
				if (contrasenia.equals(cliente.getPassword()) && mail.equals(cliente.getCorreo())) {
					ck = new Cookie("nombreUsuario", cliente.getNombre());
					sesion.setAttribute("cliente", cliente);
					response.addCookie(ck);
					response.sendRedirect("PaginaPrincipal");
				} else {
					// Si el usuario o la contraseña es incorrecta se le redirige al login de nuevo
					sesion.setAttribute("login", "Usuario o contraseña incorrectos");
					response.sendRedirect("LoginClientes");
				}
			}
		}

	}

}
