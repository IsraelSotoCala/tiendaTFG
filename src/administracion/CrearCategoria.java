package administracion;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modeloTFG.AdministradorVO;
import modeloTFG.CategoriaDAO;
import modeloTFG.CategoriaVO;

/**
 * Servlet implementation class CrearCategoria
 */
@WebServlet("/CrearCategoria")
public class CrearCategoria extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	HttpSession sesion;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		sesion = request.getSession();

		AdministradorVO admin;

		admin = (AdministradorVO) sesion.getAttribute("admin");

		// Solo se puede acceder a la página si eres admin y del rol de productos
		if (admin != null && admin.getRol().equals("productos")) {
			request.getRequestDispatcher("WEB-INF/CrearCategoria.jsp").forward(request, response);
		} else {
			response.sendRedirect("LoginEmpleados");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		sesion = request.getSession();
		
		CategoriaDAO cDAO;
		
		cDAO = new CategoriaDAO();
		
		//Definimos los campos del formulario
		
		String nombre;
		
		String descripcion;
		
		//Recogemos los campos del formulario
		
		nombre = request.getParameter("nombre");
		
		descripcion = request.getParameter("descripcion");
		
		//Creamos un VO y le asignamos los campos del formulario
		
		CategoriaVO categoria;
		
		categoria = new CategoriaVO(nombre, descripcion);
		
		//Creamos la categoría y controlamos error
		
		if(cDAO.guardarCategoria(categoria) == true) {
			sesion.setAttribute("categoriaCreada", "Categoría creada con éxito");
			response.sendRedirect("CrearCategoria");
		} else {
			sesion.setAttribute("categoriaCreada", "Error al crear categoria");
			response.sendRedirect("CrearCategoria");
		}
		
		
	}

}
