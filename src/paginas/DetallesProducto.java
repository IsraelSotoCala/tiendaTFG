package paginas;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modeloTFG.ProductoDAO;
import modeloTFG.ProductoVO;

/**
 * Servlet implementation class DetallesProducto
 */
@WebServlet("/DetallesProducto")
public class DetallesProducto extends HttpServlet {
	private static final long serialVersionUID = 1L;

	HttpSession sesion;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub HttpSession sesion;

		// Variables

		sesion = request.getSession();

		ProductoDAO pDAO;

		pDAO = new ProductoDAO();

		ProductoVO prod;

		ArrayList<ProductoVO> productos;

		productos = (ArrayList<ProductoVO>) sesion.getAttribute("productos");

		if (productos != null) {

			prod = productos.get(Integer.parseInt(request.getParameter("prod")));

			if (prod != null) {
				String nombre;

				String descripcion;

				Double precio;

				Date fechaLanzamiento;

				nombre = prod.getNombre();

				descripcion = prod.getDescripcion();

				precio = prod.getPrecio();

				fechaLanzamiento = prod.getFechaLanzamiento();

				sesion.setAttribute("producto", prod);

				request.getRequestDispatcher("WEB-INF/DetallesProducto.jsp").forward(request, response);
			} else {
				response.sendRedirect("ListaProductos");
			}
		} else {
			response.sendRedirect("PaginaPrincipal");
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.sendRedirect("Carrito");
	}

}
