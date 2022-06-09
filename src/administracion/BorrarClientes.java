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
 * Servlet implementation class BorrarClientes
 */
@WebServlet("/BorrarClientes")
public class BorrarClientes extends HttpServlet {
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

		// Solo se puede acceder a la página si eres admin y del rol de productos
		if (admin != null && admin.getRol().equals("clientes")) {
			request.getRequestDispatcher("WEB-INF/BorrarClientes.jsp").forward(request, response);
		} else {
			response.sendRedirect("LoginEmpleados");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//Variables
		
		ClienteDAO cDAO;
		
		ClienteVO cliente;
		
		ArrayList<ClienteVO> clientes;
		
		clientes = (ArrayList<ClienteVO>) sesion.getAttribute("clientes");
		
		//Recojemos el id del producto en el que hayamos pinchado
		
		String correo;
		
		int id;
		
		id = clientes.get(Integer.parseInt(request.getParameter("cli"))).getId();
		
		correo = clientes.get(Integer.parseInt(request.getParameter("cli"))).getCorreo();
		
		cDAO = new ClienteDAO();
		
		//Consultamos en la BD si existe el usuario
		
		cliente = cDAO.consultar(correo);
		
		//Si no existe mostramos error
		
		if(cliente == null) {
			sesion.setAttribute("clienteBorrado", "No se ha encontrado el cliente a borrar");
			response.sendRedirect("BorrarClientes");
		} else {
			//Si existe y se borra mostramos un mensaje diciendo que se ha borrado
			if(cDAO.borrarCliente(id) == true) {
				sesion.setAttribute("clienteBorrado", "Cliente borrado con éxito");
				response.sendRedirect("ListarClientes");
			} else {
				//Si existe un fallo al borrar lo mostramos
				sesion.setAttribute("clienteBorrado", "Error al borrar cliente");
				response.sendRedirect("BorrarClientes");
			}
		}
	}

}
