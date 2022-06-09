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
 * Servlet implementation class EditarCategoria
 */
@WebServlet("/EditarCategoria")
public class EditarCategoria extends HttpServlet {
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
		
		//Solo se puede acceder a la página si eres admin y del rol de productos
		if(admin!=null && admin.getRol().equals("productos")) {
			
			//Hacemos que los valores anteriores aparezcan en el formulario
			
			ArrayList<CategoriaVO> categorias;
			
			categorias = (ArrayList<CategoriaVO>) sesion.getAttribute("categorias");
			
			sesion.setAttribute("nombre", categorias.get(Integer.parseInt(request.getParameter("cat"))).getNombre());
			
			sesion.setAttribute("descripcion", categorias.get(Integer.parseInt(request.getParameter("cat"))).getDescripcion());
			
			request.getRequestDispatcher("WEB-INF/EditarCategoria.jsp").forward(request, response);
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
		
		CategoriaVO categoria;
		
		CategoriaVO categoriaEditada;
		
		ArrayList<CategoriaVO> categorias;
		
		categorias = (ArrayList<CategoriaVO>) sesion.getAttribute("categorias");
		
		//Definimos los campos del formulario
		
		int id = categorias.get(Integer.parseInt(request.getParameter("cat"))).getId();
		
		String nombre;
		
		String descripcion;
		
		nombre = request.getParameter("nombre");
		
		if(nombre.isEmpty()) {
			nombre = null;
		}
		
		descripcion = request.getParameter("descripcion");
		
		if(descripcion.isEmpty()) {
			descripcion = null;
		}
		
		//Editamos la categoria
		
		categoriaEditada = new CategoriaVO(nombre, descripcion);
		
		cDAO = new CategoriaDAO();
		
		if(cDAO.modificarCategoria(categoriaEditada, id) == true ) {
			sesion.setAttribute("categoriaEditada", "Categoria editada con éxito");
			response.sendRedirect("ListarCategorias");
		} else {
			sesion.setAttribute("categoriaEditada", "Error al editar categoria");
			response.sendRedirect("EditraCategoria");
		}
		
	}

}
