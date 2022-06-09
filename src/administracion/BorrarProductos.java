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
import modeloTFG.ProductoDAO;
import modeloTFG.ProductoVO;

/**
 * Servlet implementation class BorrarProductos
 */
@WebServlet("/BorrarProductos")
public class BorrarProductos extends HttpServlet {
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
			request.getRequestDispatcher("WEB-INF/BorrarProductos.jsp").forward(request, response);
		} else {
			response.sendRedirect("LoginEmpleados");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		ProductoDAO pDAO;
		
		ProductoVO producto;
		
		ArrayList<ProductoVO> productos;
		
		productos = (ArrayList<ProductoVO>) sesion.getAttribute("productos");
		
		//Recojemos el id del producto en el que hayamos pinchado
		
		int id;
		
		id = productos.get(Integer.parseInt(request.getParameter("prod"))).getId();
		
		pDAO = new ProductoDAO();
		
		producto = pDAO.consultar(id);
		
		if(producto == null) {
			sesion.setAttribute("productoBorrado", "No se ha encontrado el producto a borrar");
			response.sendRedirect("BorrarProductos");
		} else {
			if(pDAO.borrarProducto(id) == true) {
				sesion.setAttribute("productoBorrado", "Producto borrado con éxito");
				response.sendRedirect("ListarProductos");
			} else {
				sesion.setAttribute("productoBorrado", "Error al borrar producto");
				response.sendRedirect("BorrarProducto");
			}
		}
	}

}
