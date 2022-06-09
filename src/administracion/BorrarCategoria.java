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
import modeloTFG.CategoriaDAO;
import modeloTFG.CategoriaVO;

/**
 * Servlet implementation class BorrarProducto
 */
@WebServlet("/BorrarCategoria")
public class BorrarCategoria extends HttpServlet {
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
		if (admin != null && admin.getRol().equals("productos")) {
			request.getRequestDispatcher("WEB-INF/BorrarCategoria.jsp").forward(request, response);
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
		
		CategoriaDAO cDAO;
		
		CategoriaVO categoria;
		
		ArrayList<CategoriaVO> categorias;
		
		categorias = (ArrayList<CategoriaVO>) sesion.getAttribute("categorias");
		
		//Recogemos el id de la categoria
		
		int id;
		
		id = categorias.get(Integer.parseInt(request.getParameter("cat"))).getId();
		
		cDAO = new CategoriaDAO();
		
		//Comprobamos que existe una categoria con el id que recogemos
		
		categoria = cDAO.consultar(id);
		
		//Si no encontramos la categoria mostramos error
		
		if(categoria == null) {
			sesion.setAttribute("categoriaBorrada", "No se ha encontrado la categoria a borrar");
			response.sendRedirect("BorrarCategoria");
		} else {
			//Mostramos un mensaje y redireccionamos si la categoria se borra
			if(cDAO.borrarCategoria(id) == true) {
				sesion.setAttribute("categoriaBorrada", "Categoria borrada con éxito");
				response.sendRedirect("ListarCategorias");
			} else {
				//Si no se borra mostramos error
				sesion.setAttribute("categoriaBorrada", "Fallo al borrar categoria");
				response.sendRedirect("BorrarCategoria");
			}
		}
	}

}
