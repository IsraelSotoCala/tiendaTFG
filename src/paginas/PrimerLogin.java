package paginas;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modeloTFG.ClienteDAO;
import modeloTFG.ClienteVO;

/**
 * Servlet implementation class PrimerLogin
 */
@WebServlet("/PrimerLogin")
public class PrimerLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	HttpSession sesion;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		sesion = request.getSession();

		ClienteVO cliente;

		cliente = (ClienteVO) sesion.getAttribute("cliente");

		if (cliente != null) {
			// Si no es la primera vez que se logea el cliente lo mandamos al login
			if (cliente.getPrimeraVez() == true) {
				request.getRequestDispatcher("WEB-INF/PrimerLogin.jsp").forward(request, response);
			} else {
				response.sendRedirect("LoginClientes");
			}
		} else {
			response.sendRedirect("LoginClientes");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		sesion = request.getSession();

		String codigo;

		codigo = request.getParameter("codigo");

		ClienteVO cliente;

		cliente = (ClienteVO) sesion.getAttribute("cliente");

		ClienteDAO clientes;

		clientes = new ClienteDAO();

		// Si el codigo introducido es el correcot le enviamos a la página principal
		if (codigo.equals(cliente.getCodigo())) {
			// Una vez verificado con éxito le borramos el código de la bd y le verificamos
			clientes.primerInicio(cliente.getCorreo());
			response.sendRedirect("PaginaPrincipal");
		} else {
			// Si el codigo no es correcto, mostramos un mensaje de error
			sesion.setAttribute("loginPrimero", "El código introducido no es el correcto");
			response.sendRedirect("PrimerLogin");
		}
	}

}
